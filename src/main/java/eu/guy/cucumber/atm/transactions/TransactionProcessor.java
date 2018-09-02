package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.domain.BusinessException;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;

// TODO change to full featured threaded class, disallow anyone to run looped
// method
public class TransactionProcessor {
    private static TransactionQueue queue = new TransactionQueue();
    private static Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        TransactionProcessor.processQueue();
    }

    //    Carry out transactions written to queue
    private static void processQueue() throws
            FileNotFoundException, BusinessException {
        String message, trnType;
        Money trnAmount, balance;
        Instruction inst;

//        !! Forever loop, should run in a thread
        while (true) {
            Utils.sleep(1);
            message = queue.read();
            if (message == null)
                continue;
            inst = new Instruction(message);
            trnType = inst.getType();
            trnAmount = Money.convert(inst.getAmount());
            balance = BalanceStore.getBalance();
            log.info(String.format("Transaction type=%s, amount=%s",
                    trnType, trnAmount));
            switch (trnType) {
                case "+":
                    balance.add(trnAmount);
                    break;
                case "-":
                    balance.subtract(trnAmount);
                    break;
                default:
                    log.error("Unknown type of transaction");
                    return;
            }
            BalanceStore.setBalance(balance);
        }
    }

    public static Thread getProcessQueueThreaded() {
        Thread daemon = new Thread(() -> {
            try {
                TransactionProcessor.processQueue();
            } catch (FileNotFoundException | BusinessException e) {
                e.printStackTrace();
            }
        });
        return daemon;
    }
}
