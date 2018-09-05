package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.KnowsTheDomain;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import eu.guy.cucumber.atm.transactions.BalanceStore;
import eu.guy.cucumber.atm.utils.Utils;

import java.io.FileNotFoundException;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class AccountSteps {

    private KnowsTheDomain helper;

    /*
        Cucumber injects instance (singleton) through DI with picocontainer
        To use it, just include appropriate jar

        One injected instance per Scenario
    */
// TODO replace outdated pico with Spring Boot DI, common config
    public AccountSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @Given("^[Mm]y account has been credited with (\\$\\d+(?:\\.\\d+)?)$")
    public void iHaveCredited$InMyAccount(
            @Transform(MoneyConverter.class) Money money) throws Exception {
        helper.getMyAccount().credit(money);
    }

    //    handle asynchronous transactions processing
    public static void waitForBalanceNoErr(Money expected) {
        Money balance;
        Duration duration = Duration.ofSeconds(5);
        while (!duration.isNegative()) {
            try {
                balance = BalanceStore.getBalance();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                do not raise error
                return;
            }
            if (balance.equals(expected)) {
                return;
            }
            Utils.sleep(1);
            duration = duration.minusSeconds(1);
        }
    }

    @Then("^the balance of my account should be (\\$[\\d.]+)$")
    public void theBalanceOfMyAccountShouldBe(
            @Transform(MoneyConverter.class) Money money) {
        waitForBalanceNoErr(money);
        assertEquals(helper.getMyAccount().getBalance(), money);
    }
}
