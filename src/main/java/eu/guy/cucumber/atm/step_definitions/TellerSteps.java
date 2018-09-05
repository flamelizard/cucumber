package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.guy.cucumber.atm.domain.KnowsTheDomain;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;

import static org.junit.Assert.assertEquals;


public class TellerSteps {
    private KnowsTheDomain helper;

    public TellerSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @When("^I withdraw \\$([\\d.]+)$")
    public void iWithdraw$(
            @Transform(MoneyConverter.class) Money amount) throws Throwable {
        helper.getTeller().withdrawFrom(helper.getMyAccount(), amount);
    }

    @When("^I check my balance")
    public void iCheckBalance() {
        WebTeller teller = (WebTeller) helper.getTeller();
        teller.checkBalance();
    }

    @Then("^I should see that my balance is (\\$[\\d.]+)$")
    public void iSeeBalance(@Transform(MoneyConverter.class) Money balance) {
        WebTeller wTeller = (WebTeller) helper.getTeller();
        assertEquals(balance, wTeller.getDisplayedBalance());
    }
}
