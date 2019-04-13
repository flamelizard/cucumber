package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.common.Utils;
import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

// enough annotating one step / hook class
@ContextConfiguration("classpath:cucumber.xml")
public class AccountSteps {
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
            Utils.sleep(1);
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
            Utils.sleep(1);
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
}