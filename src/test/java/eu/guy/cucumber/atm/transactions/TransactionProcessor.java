package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.BusinessException;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.server.DataStore;
import eu.guy.cucumber.atm.transactions.events.Event;

import static eu.guy.cucumber.atm.common.Utils.sleep;
import static eu.guy.cucumber.atm.transactions.events.EventLogger.logEvent;

// TODO turn to threaded class to prevent running processQueue directly
public class TransactionProcessor {

    public static void main(String[] args) throws Exception {
        TransactionProcessor.processQueue();
    }

    private static void processQueue() throws BusinessException {
        String trnType;
        Money trnAmount, balance;
        Transaction trn;
        Account toAccount;

//        !! Forever loop, should run in a thread
        while (true) {
            trn = TransactionHandler.getPending();
            if (trn == null) {
                sleep(2);
                continue;
            }
//            System.out.println("[process-trn " + trn);
            trnType = trn.getType();
            trnAmount = Money.convert(String.valueOf(trn.getAmount()));
            toAccount = Account.getAccountOrNull(trn.getAccNum());
            if (toAccount == null) {
                trn.failed();
                throw new BusinessException("Account does not exists: " + trn.getAccNum());
            }
            balance = toAccount.getBalance();
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
                        continue;
                    }
                    break;
                default:
                    logEvent(new Event("failure", trn.getTrnId())
                            .add("message", "Unknown type of transaction"));
                    trn.failed();
                    continue;
            }
            toAccount.setBalance(balance);
            trn.completed();
            logEvent(new Event("transaction", trn.getTrnId())
                    .add("trnType", trnType)
                    .add("trnAmount", trnAmount.toString()));
        }
    }

    public static Thread getProcessQueueThreaded() {
        return new Thread(() -> {
            DataStore.createConnection();
            try {
                TransactionProcessor.processQueue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
