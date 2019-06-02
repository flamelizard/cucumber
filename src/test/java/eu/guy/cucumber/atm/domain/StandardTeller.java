package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.TransactionHandler;
import eu.guy.cucumber.atm.transactions.events.EventLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class StandardTeller implements Teller {
    private CashSlot cashSlot;
    private Logger log = LogManager.getLogger(StandardTeller.class);

    public StandardTeller(CashSlot cashSlot) {
        this.cashSlot = cashSlot;
    }

    public void withdrawFrom(Account account, Money amount) throws RuntimeException {
        Integer trnId = account.debit(amount);
        Map<String, String> event = EventLogger.waitForEvent(trnId, 3);
        if (event == null) {
            log.warn("Transaction not found <" + trnId + ">");
            return;
        }

        String trnType = event.getOrDefault("type", "");
        switch (trnType) {
            case "transaction":
                try {
                    cashSlot.dispense(amount);
                    break;
                } catch (RuntimeException ex) {
                    TransactionHandler.resetTransaction(trnId);
                    throw ex;
                }
            case "failure":
                String errorMsg = event.get("message");
                throw new RuntimeException(errorMsg);
            default:
                log.warn("Unknown transaction status <" + trnId + ">");
        }
    }
}
