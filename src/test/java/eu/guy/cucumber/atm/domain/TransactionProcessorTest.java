package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.BalanceStore;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import eu.guy.cucumber.atm.transactions.TransactionQueue;
import eu.guy.cucumber.atm.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TransactionProcessorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private KnowsTheDomain domain;
    private Account account;
    private Thread processor;

    @Before
    public void setup() throws IOException {
        TransactionQueue.init();
        BalanceStore.init();
        domain = new KnowsTheDomain();
        account = domain.getMyAccount();
        processor = TransactionProcessor.getProcessQueueThreaded();
        processor.start();
    }

    @After
    public void teardown() {
        processor.interrupt();
    }

    @Test
    public void canProcessCreditTrans() throws Exception {
        account.credit(new Money(100, 0));
        Utils.sleep(2);
        assertEquals(new Money(100, 0), account.getBalance());
    }

    @Test
    public void canProcessMultipleTransactions() throws Exception {
        account.credit(new Money(200, 0));
        account.debit(new Money(50, 0));
        account.debit(new Money(5, 0));

        Utils.sleep(5);

        assertEquals(new Money(145, 0), account.getBalance());
    }

//    TODO cannot leak exception from a thread. Workaround it.
//    @Test
//    public void preventDebitGreaterThanBalance() throws Exception {
//        account.debit(new Money(999, 99));
//
//        thrown.expect(BusinessException.class);
//        TransactionProcessor.processQueue();
//    }

}