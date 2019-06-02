package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.TestCashSlot;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertTrue(teller.isDisplaying("insufficient funds"));
    }

    @But("the cash slot has developed a fault")
    public void cashSlotIsFaulty() {
        ((TestCashSlot) slot).injectFault();
    }

    @Given("ATM contains (\\$[\\d.]+)")
    public void feedATM(@Transform(MoneyConverter.class) Money funds) {
        slot.load(funds);
    }
}
