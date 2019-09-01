package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import eu.guy.cucumber.atm.domain.AtmGui;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.TestCashSlot;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CashslotSteps {
    @Autowired
    private CashSlot slot;
    @Autowired
    private AtmGui teller;

    @Then("{money} should be dispensed")
    public void $ShouldBeDispensed(Money amount) throws Throwable {
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

    @Given("ATM contains {money}")
    public void feedATM(Money funds) {
        slot.load(funds);
    }
}
