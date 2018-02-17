package eu.guy.cucumber.nicebank.step_definitions;

import cucumber.api.java.en.Then;
import eu.guy.cucumber.nicebank.domain.KnowsTheDomain;
import org.junit.Assert;

/**
 * Created by Tom on 2/17/2018.
 */
public class CashslotSteps {
    private KnowsTheDomain helper;

    public CashslotSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @Then("^\\$(\\d+) should be dispensed$")
    public void $ShouldBeDispensed(int amount) throws Throwable {
        Assert.assertEquals(amount, helper.getCashSlot().getContents());
    }
}
