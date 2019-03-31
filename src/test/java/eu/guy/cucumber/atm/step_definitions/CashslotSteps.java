package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;


public class CashslotSteps {

    private final CashSlot slot;
    private final WebTeller teller;

    //    Pico-container injects to Steps and Hooks
    public CashslotSteps(CashSlot slot, WebTeller teller) {
        this.slot = slot;
        this.teller = teller;
    }

    @Then("^\\$([\\d.]+) should be dispensed$")
    public void $ShouldBeDispensed(
            @Transform(MoneyConverter.class) Money amount) throws Throwable {
        assertEquals(amount, slot.getContents());
    }

    @Then("I should be told that I have insufficient funds")
    public void customerHasInsufficientFunds() {
        Assert.assertTrue(teller.accountHasInsufficientFunds());
//        assertThat(teller.getCurrentScreenHtml(),
//                containsString("insufficient funds"));
    }
}
