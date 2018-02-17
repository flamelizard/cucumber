package eu.guy.cucumber.nicebank.step_definitions;

import cucumber.api.java.en.When;
import eu.guy.cucumber.nicebank.domain.KnowsTheDomain;

/**
 * Created by Tom on 2/17/2018.
 */
public class TellerSteps {
    private KnowsTheDomain helper;

    public TellerSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @When("^I withdraw \\$(\\d+)$")
    public void iWithdraw$(int amount) throws Throwable {
        helper.getTeller().withdrawFrom(helper.getMyAccount(), amount);
    }
}
