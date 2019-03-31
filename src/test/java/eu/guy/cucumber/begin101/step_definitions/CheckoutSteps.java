package eu.guy.cucumber.begin101.step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.guy.cucumber.begin101.implementation.Checkout;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 11/15/2017.
 */
public class CheckoutSteps {
    private Checkout checkout = new Checkout();
    private Map<String, Integer> priceList = new HashMap<>();

    @Given("^the price of a \"([^\"]*)\" is (\\d+)Kc$")
    public void thePriceOfAIsKc(String product, int price) throws Throwable {
        priceList.put(product, price);
    }

    @When("^I checkout (\\d+) \"([^\"]*)\"$")
    public void iCheckout(int count, String product) throws Throwable {
        checkout.add(count, priceList.get(product));
    }

    @Then("^total price should be (\\d+)Kc$")
    public void totalPriceShouldBeKc(int price) throws Throwable {
        assertEquals(price, checkout.total());
    }
}
