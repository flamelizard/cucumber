package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.server.DataStore;
import eu.guy.cucumber.atm.transactions.events.EventLogger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO consider moving code back to /java since major part is production code sort of
public class TransactionHandlerTest {
    @BeforeClass
    public static void setup() {
        DataStore.createConnection();
        DataStore.deleteTransactions();
        DataStore.deleteAccounts();
    }

    @Test
    public void canCreateAndReadTrans() {
        int trnId = TransactionHandler.writeTrn(99, "-", 100.0);
        Transaction trn = TransactionHandler.readById(trnId);

        assertEquals(99, trn.getAccNum());
        assertEquals("-", trn.getType());
        assertEquals(100.0, trn.getAmount(), 0.05);
    }

    @Test
    public void canRollbackTran() {
        Thread processor = TransactionProcessor.getProcessQueueThreaded();
        processor.start();
        Account acc = Account.createAccount(55);

        int t1 = TransactionHandler.writeTrn(acc.getAccountNum(), "+", 100);
        EventLogger.waitForEvent(t1, 5);
        assertEquals(100, acc.getBalance().getAmount(), 0.1);

        TransactionHandler.resetTrn(t1);

        assertEquals(0f, acc.getBalance().getAmount(), 0.1);
    }
}