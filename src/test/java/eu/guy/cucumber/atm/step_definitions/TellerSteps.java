package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.AtmGui;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TellerSteps {
    @Autowired
    private AtmGui teller;
    @Autowired
    private Account account;
    private Money balance;

    @When("^I withdraw \\$([\\d.]+)$")
    public void iWithdraw(@Transform(MoneyConverter.class) Money amount) {
        teller.withdrawFrom(account, amount);
    }

    @When("^I type \\$([\\d.]+)$")
    public void iType(@Transform(MoneyConverter.class) Money amount) {
        teller.fillWithdrawAmount(account, amount);
    }

    @When("^I check my balance")
    public void iCheckBalance() {
        balance = teller.checkBalance(account);
    }

    @Then("^I should see that my balance is (\\$[\\d.]+)$")
    public void iSeeBalance(@Transform(MoneyConverter.class) Money expected) {
        assertEquals(expected, balance);
    }

    @Then("I should see an out-of-order message")
    public void iSeeOutOfOrderMessage() {
        assertTrue(teller.isDisplaying("Out of order"));
    }

    @Then("I should see ask-for-less message")
    public void seeAskForLessMsg() {
        assertTrue(teller.isDisplaying("Ask for less"));
    }
}
