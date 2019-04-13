package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class CashslotSteps {
    @Autowired
    private CashSlot slot;
    @Autowired
    private WebTeller teller;

    @Then("^\\$([\\d.]+) should be dispensed$")
    public void $ShouldBeDispensed(
            @Transform(MoneyConverter.class) Money amount) throws Throwable {
        assertEquals(amount, slot.getContents());
    }

    @Then("I should be told that I have insufficient funds")
    public void customerHasInsufficientFunds() {
        Assert.assertTrue(teller.accountHasInsufficientFunds());
    }
}
