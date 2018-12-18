package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.BalanceStore;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import eu.guy.cucumber.atm.transactions.TransactionQueue;
import eu.guy.cucumber.atm.transactions.events.EventLogger;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static eu.guy.cucumber.atm.step_definitions.AccountSteps.waitForBalance;
import static org.junit.Assert.assertEquals;

// TODO intermittent error to read log file due to threads race condition
public class TransactionProcessorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private KnowsTheDomain domain;
    private Account account;
    private static Thread processor;

    @BeforeClass
    public static void runTransactionDaemon() {
        processor = TransactionProcessor.getProcessQueueThreaded();
        processor.start();
    }

    @AfterClass
    public static void stopDaemon() {
        processor.interrupt();
    }

    @Before
    public void setup() throws IOException {
        TransactionQueue.init();
        BalanceStore.init();
        domain = new KnowsTheDomain();
        account = domain.getMyAccount();
    }

    @Test
    public void canProcessCreditTrans() {
        Money money = new Money(100, 0);
        account.credit(money);
        waitForBalance(money);
        assertEquals(money, account.getBalance());
    }

    @Test
    public void canProcessMultipleTransactions() {
        account.credit(new Money(200, 0));
        account.debit(new Money(50, 0));
        account.debit(new Money(5, 0));
        waitForBalance(new Money(145, 0));

        assertEquals(new Money(145, 0), account.getBalance());
    }

    @Test
    public void preventDebitGreaterThanBalance() {
        account.credit(new Money(1, 0));
        Integer trnId = account.debit(new Money(999, 99));

        Map<String, String> event = EventLogger.waitForEvent(trnId, 5);

        Optional<Map<String, String>> opt = Optional.ofNullable(event);
        assertEquals("failure", opt.orElse(new HashMap<>()).get("type"));
    }
}