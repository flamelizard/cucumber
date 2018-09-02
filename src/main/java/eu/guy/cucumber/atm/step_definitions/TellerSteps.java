package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.guy.cucumber.atm.domain.BusinessException;
import eu.guy.cucumber.atm.domain.KnowsTheDomain;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.step_definitions.transforms.MoneyConverter;
import eu.guy.cucumber.atm.transactions.BalanceStore;

import java.time.Duration;

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

    //    TODO handle transactions asynchronicity more gracefully, usable in
// unittests too
    @Then("^Wait to process transactions, expect balance ($[\\d]+)$")
    public void waitToProcessTransactions(@Transform(
            MoneyConverter.class) Money expected) throws Exception {
        Money balance;
        Duration duration = Duration.ofSeconds(5);
        while (!duration.isNegative()) {
            balance = BalanceStore.getBalance();
            if (balance.equals(expected))
                return;
            duration = duration.minusSeconds(1);
        }
        throw new BusinessException("Some transactions have not been " +
                "processed");
    }

}
