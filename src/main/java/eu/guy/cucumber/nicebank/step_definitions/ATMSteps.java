package eu.guy.cucumber.nicebank.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.guy.cucumber.nicebank.step_definitions.transforms.MoneyConverter;
import eu.guy.cucumber.nicebank.support.KnowsTheDomain;
import eu.guy.cucumber.nicebank.support.Money;
import org.junit.Assert;

/**
 * Created by Tom on 2/11/2018.
 */
public class ATMSteps {

    private final KnowsTheDomain helper;

    public ATMSteps() {
        helper = new KnowsTheDomain();
    }

    @Given("^I have deposited (\\$\\d+\\.\\d+) in my account$")
    public void iHaveDeposited$InMyAccount(
            @Transform(MoneyConverter.class) Money money) throws Throwable {
        helper.getMyAccount().deposit(money);
        Assert.assertEquals(money, helper.getMyAccount().getBalance());
    }

    @When("^I withdraw \\$(\\d+)$")
    public void iWithdraw$(int amount) throws Throwable {
        helper.getTeller().withdrawFrom(helper.getMyAccount(), amount);
    }

    @Then("^\\$(\\d+) should be dispensed$")
    public void $ShouldBeDispensed(int amount) throws Throwable {
        Assert.assertEquals(amount, helper.getCashSlot().getContents());
    }
}
