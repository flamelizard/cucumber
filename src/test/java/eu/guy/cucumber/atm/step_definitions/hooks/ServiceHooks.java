package eu.guy.cucumber.atm.step_definitions.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.server.ATMServer;
import org.openqa.selenium.OutputType;

public class ServiceHooks {
    public static int WEBPORT = 9988;
    private final CashSlot slot;
    private final WebTeller teller;
    private ATMServer server;

    public ServiceHooks(CashSlot slot, WebTeller teller) {
        this.slot = slot;
        this.teller = teller;
    }

    //    Annotating feature will automatically run these methods on each scenario
    @Before("@atm-web")
    public void startServer() throws Exception {
        System.out.println("Starting ATM Backend ... port " + WEBPORT);
        server = new ATMServer(WEBPORT, slot);
        server.start();
        teller.goToMain();
    }

    @After("@atm-web")
    public void stopServer() throws Exception {
        System.out.println("Stopping ATM Backend ___ ");
        server.stop();
    }

    @After
    public void cleanUp(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = teller.getWebDriver().getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); // save image
        }
        teller.close();
    }
}
