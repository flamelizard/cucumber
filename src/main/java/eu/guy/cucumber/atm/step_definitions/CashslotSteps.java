package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.KnowsTheDomain;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;


public class CashslotSteps {
    private KnowsTheDomain helper;

    public CashslotSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @Then("^\\$([\\d.]+) should be dispensed$")
    public void $ShouldBeDispensed(
            @Transform(MoneyConverter.class) Money amount) throws Throwable {
        assertEquals(amount, helper.getCashSlot().getContents());
    }

    @Then("I should be told that I have insufficient funds")
    public void customerHasInsufficientFunds() {
        WebTeller teller = (WebTeller) helper.getTeller();
        Assert.assertTrue(teller.accountHasInsufficientFunds());
//        assertThat(teller.getCurrentScreenHtml(),
//                containsString("insufficient funds"));
    }
}
