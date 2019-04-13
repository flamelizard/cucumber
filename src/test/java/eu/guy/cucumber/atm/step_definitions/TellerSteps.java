package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;


public class TellerSteps {
    @Autowired
    private WebTeller teller;
    @Autowired
    private Account account;

    @When("^I withdraw \\$([\\d.]+)$")
    public void iWithdraw(@Transform(MoneyConverter.class) Money amount) {
        teller.withdrawFrom(account, amount);
    }

    @When("^I check my balance")
    public void iCheckBalance() {
        teller.checkBalance(account);
    }

    @Then("^I should see that my balance is (\\$[\\d.]+)$")
    public void iSeeBalance(@Transform(MoneyConverter.class) Money balance) {
        assertEquals(balance, teller.getDisplayedBalance());
    }
}
