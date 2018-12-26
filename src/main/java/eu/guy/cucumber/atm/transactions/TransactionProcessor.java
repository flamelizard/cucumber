package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.BusinessException;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.server.DataStore;
import eu.guy.cucumber.atm.transactions.events.Event;

import static eu.guy.cucumber.atm.transactions.events.EventLogger.logEvent;
import static eu.guy.cucumber.atm.utils.Utils.sleep;

// TODO turn to threaded class to prevent running processQueue directly
public class TransactionProcessor {
    private static TransactionQueue queue = new TransactionQueue();

    public static void main(String[] args) throws Exception {
        TransactionProcessor.processQueue();
    }

    private static void processQueue() throws BusinessException {
        String trnType;
        Money trnAmount, balance;
        Instruction inst;
        Account toAccount;

//        !! Forever loop, should run in a thread
        while (true) {
            inst = queue.read();
            if (inst == null) {
                sleep(1);
                continue;
            }
            trnType = inst.getType();
            trnAmount = Money.convert(inst.getAmount());
            toAccount = Account.getAccount(inst.getAccNumber());
            balance = toAccount.getBalance();
            switch (trnType) {
                case "+":
                    balance.add(trnAmount);
                    break;
                case "-":
                    try {
                        balance.subtract(trnAmount);
                    } catch (BusinessException ex) {
                        logEvent(new Event("failure", inst.getId())
                                .add("message", ex.getMessage()));
                        continue;
                    }
                    break;
                default:
                    logEvent(new Event("failure", inst.getId())
                            .add("message", "Unknown type of transaction"));
                    return;
            }
            toAccount.setBalance(balance);
            logEvent(new Event("transaction", inst.getId())
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
