package eu.guy.cucumber.atm.tests;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.server.DataStore;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import eu.guy.cucumber.atm.transactions.events.EventLogger;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static eu.guy.cucumber.atm.step_definitions.AccountSteps.assertForBalance;
import static org.junit.Assert.assertEquals;

public class TransactionProcessorTest {
    private static TransactionProcessor processor;
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Account testAccount;

    @BeforeClass
    public static void runTransactionDaemon() {
        DataStore.createConnection();
        processor = new TransactionProcessor();
        processor.go();
    }

    @AfterClass
    public static void stopDaemon() {
        processor.quit();
    }

    @Before
    public void setup() {
        DataStore.deleteAccounts();
        testAccount = Account.createAccount(567);
    }

    @Test
    public void canProcessCreditTrans() {
        Money money = new Money(100, 0);
        testAccount.credit(money);
        assertForBalance(money, testAccount);
    }

    @Test
    public void canProcessMultipleTransactions() {
        testAccount.credit(new Money(200, 0));
        testAccount.debit(new Money(50, 0));
        testAccount.debit(new Money(5, 0));
        assertForBalance(new Money(145, 0), testAccount);
    }

    @Test
    public void canProcessMultipleAccountTransactions() {
        Account acc1 = Account.createAccount(1);
        Account acc2 = Account.createAccount(2);
        Account acc3 = Account.createAccount(999);

        acc1.credit(new Money(10));
        acc2.credit(new Money(20));
        acc3.credit(new Money(99));

        acc1.debit(new Money(5));
        acc3.debit(new Money(9));

        assertForBalance(new Money(5), acc1);
        assertForBalance(new Money(20), acc2);
        assertForBalance(new Money(90), acc3);
    }

    @Test
    public void preventDebitGreaterThanBalance() {
        testAccount.credit(new Money(1, 0));
        Integer trnId = testAccount.debit(new Money(999, 99));

        Map<String, String> event = EventLogger.waitForEvent(trnId, 5);

        Optional<Map<String, String>> opt = Optional.ofNullable(event);
        assertEquals("failure", opt.orElse(new HashMap<>()).get("type"));
    }
}