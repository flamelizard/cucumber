package eu.guy.cucumber.atm.tests;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.server.DataStore;
import eu.guy.cucumber.atm.transactions.Transaction;
import eu.guy.cucumber.atm.transactions.TransactionHandler;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import eu.guy.cucumber.atm.transactions.WaitingTrans;
import eu.guy.cucumber.atm.transactions.events.EventLogger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO consider moving code back to /java since major part is production code sort of
public class TransactionHandlerTest {
    @BeforeClass
    public static void connectDb() {
        DataStore.createConnection();
    }

    @Before
    public void cleanUp() {
        DataStore.deleteAccounts();
        DataStore.deleteTransactions();
    }

    @Test
    public void canCreateAndReadTrans() {
        int trnId = TransactionHandler.writeTrn(99, "-", 100.0);
        Transaction trn = TransactionHandler.getTransaction(trnId);

        assertEquals(99, trn.getAccNum());
        assertEquals("-", trn.getType());
        assertEquals(100.0, trn.getAmount(), 0.05);
    }

    @Test
    public void canRollbackTran() {
        TransactionProcessor processor = new TransactionProcessor();
        processor.go();
        Account acc = Account.createAccount(55);

        int t1 = TransactionHandler.writeTrn(acc.getAccountNum(), "+", 100);
        EventLogger.waitForEvent(t1, 5);
        assertEquals(100, acc.getBalance().getAmount(), 0.1);

        TransactionHandler.resetTransaction(t1);

        assertEquals(0f, acc.getBalance().getAmount(), 0.1);
        processor.quit();
    }

    @Test
    public void returnWaitingTransactions() {
        TransactionHandler.writeTrn(801, "-", 100);
        TransactionHandler.writeTrn(802, "+", 200);

        WaitingTrans trans = new WaitingTrans();
        assertEquals(2, trans.get().size());
        assertEquals(0, trans.get().size());

        TransactionHandler.writeTrn(803, "-", 300);
        assertEquals(1, trans.get().size());
        assertEquals(0, trans.get().size());
    }
}