package eu.guy.cucumber.atm.domain.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.KnowsTheDomain;
import eu.guy.cucumber.atm.server.ATMServer;
import eu.guy.cucumber.atm.server.DataStore;
import org.openqa.selenium.OutputType;

public class ServiceHooks {
    public static int WEBPORT = 9988;
    private ATMServer server;
    private KnowsTheDomain helper;

    public ServiceHooks(KnowsTheDomain helper) {
        this.helper = helper;
    }

    //    Annotating feature will automatically run these methods on each scenario
    @Before("@atm-web")
    public void startServer() throws Exception {
        System.out.println("Starting ATM Backend ... port " + WEBPORT);
        server = new ATMServer(WEBPORT, helper.getCashSlot());
        server.start();
    }

    @After("@atm-web")
    public void stopServer() throws Exception {
        System.out.println("Stopping ATM Backend ___ ");
        server.stop();
    }

    @After
    public void finish(Scenario scenario) {
        byte[] screenshot = helper.getWebDriver()
                .getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png"); // save image
        helper.getWebDriver().close();
    }

    @Before
    public void prepareDataStore() {
        DataStore.createConnection();
        System.out.println("Deleting all accounts...");
        Account.deleteAll();
    }
}
