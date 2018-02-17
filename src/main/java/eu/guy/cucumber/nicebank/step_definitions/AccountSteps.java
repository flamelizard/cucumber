package eu.guy.cucumber.nicebank.step_definitions;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import eu.guy.cucumber.nicebank.domain.KnowsTheDomain;
import eu.guy.cucumber.nicebank.domain.Money;
import eu.guy.cucumber.nicebank.step_definitions.transforms.MoneyConverter;
import org.junit.Assert;

/**
 * Created by Tom on 2/17/2018.
 */
public class AccountSteps {

    private KnowsTheDomain helper;

    //    Cucumber injects instance through DI with picocontainer
//    Instance is kind of singleton, shared within steps in the scenario
    public AccountSteps(KnowsTheDomain helper) {
        this.helper = helper;
    }

    @Given("^I have deposited (\\$\\d+\\.\\d+) in my account$")
    public void iHaveDeposited$InMyAccount(
            @Transform(MoneyConverter.class) Money money) throws Throwable {
        helper.getMyAccount().deposit(money);
        Assert.assertEquals(money, helper.getMyAccount().getBalance());
    }
}
