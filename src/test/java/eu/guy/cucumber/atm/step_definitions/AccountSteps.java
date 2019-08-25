package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.common.Common;
import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

// enough annotating one step / hook class
@ContextConfiguration("classpath:spring-config.xml")
public class AccountSteps {
    private int DEFAULT_BALANCE = 100;

    @Autowired
    private Account testAccount;

    public static void waitForBalance(Money expected, Account account) {
        Money balance;
        Duration duration = Duration.ofSeconds(5);
        while (!duration.isNegative()) {
            balance = account.getBalance();
            if (balance.equals(expected)) {
                return;
            }
            Common.sleep(1);
            duration = duration.minusSeconds(1);
        }
    }

    //    handle asynchronous transactions processing
    public static void assertForBalance(Money expected, Account account) {
        Money balance = new Money(0);
        Duration duration = Duration.ofSeconds(5);
        while (!duration.isNegative()) {
            balance = account.getBalance();
            if (balance.equals(expected)) {
                break;
            }
            Common.sleep(1);
            duration = duration.minusSeconds(1);
        }
        assertEquals(expected, balance);
    }

    @Given("^[Mm]y account has been credited with (\\$\\d+(?:\\.\\d+)?)$")
    public void iHaveCredited$InMyAccount(
            @Transform(MoneyConverter.class) Money money) throws Exception {
        testAccount.credit(money);
        waitForBalance(money, testAccount);
    }

    @Then("^the balance of my account should be (\\$[\\d.]+)$")
    public void theBalanceOfMyAccountShouldBe(
            @Transform(MoneyConverter.class) Money money) {
        waitForBalance(money, testAccount);
        assertEquals(money, testAccount.getBalance());
    }

    @Given("^my account is in credit$")
    public void accountInCredit() {
        Money credit = new Money(DEFAULT_BALANCE);
        testAccount.credit(credit);
        waitForBalance(credit, testAccount);
    }

    @Then("my balance is unchanged")
    public void balanceUnchanged() {
        assertEquals(Float.valueOf(DEFAULT_BALANCE),
                testAccount.getBalance().getAmount());
    }
}
