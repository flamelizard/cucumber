package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.BusinessException;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.server.DataStore;
import eu.guy.cucumber.atm.transactions.events.Event;

import static eu.guy.cucumber.atm.transactions.events.EventLogger.logEvent;

// TODO low, nest the thread, do not expose its methods
public class TransactionProcessor extends Thread {

    @Override
    public void run() {
        DataStore.createConnection();
        WaitingTrans waiting = new WaitingTrans();
        while (!isInterrupted()) {
            for (Transaction trn : waiting.get()) {
                try {
                    processTransaction(trn);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
//                re-init interruption to leave the while
                this.interrupt();
            }
        }
    }

    private void processTransaction(Transaction trn) throws BusinessException {
        String trnType = trn.getType();
        Money trnAmount = Money.convert(String.valueOf(trn.getAmount()));
        Account toAccount = Account.getAccountOrNull(trn.getAccNum());
        if (toAccount == null) {
            trn.failed();
            throw new BusinessException("Account does not exists: " + trn.getAccNum());
        }
        Money balance = toAccount.getBalance();
        switch (trnType) {
            case "+":
                balance.add(trnAmount);
                break;
            case "-":
                try {
                    balance.subtract(trnAmount);
                } catch (BusinessException ex) {
                    logEvent(new Event("failure", trn.getTrnId())
                            .add("message", ex.getMessage()));
                    trn.failed();
                    return;
                }
                break;
            default:
                logEvent(new Event("failure", trn.getTrnId())
                        .add("message", "Unknown type of transaction"));
                trn.failed();
                return;
        }
        toAccount.setBalance(balance);
        trn.completed();
        logEvent(new Event("transaction", trn.getTrnId())
                .add("trnType", trnType)
                .add("trnAmount", trnAmount.toString()));
    }

    public void go() {
        this.start();
    }

    public void quit() {
//        sends InterruptException and sets flag isInterrupted
        this.interrupt();
    }
}
