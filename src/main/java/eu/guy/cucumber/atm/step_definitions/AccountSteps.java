package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.KnowsTheDomain;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;

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
            @Transform(MoneyConverter.class) Money money) {
        helper.getMyAccount().credit(money);
    }

    @Then("^the balance of my account should be (\\$[\\d.]+)$")
    public void theBalanceOfMyAccountShouldBe$(
            @Transform(MoneyConverter.class) Money money) {
        assertEquals(helper.getMyAccount().getBalance(), money);
    }
}
