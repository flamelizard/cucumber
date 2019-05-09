package eu.guy.cucumber.atm.step_definitions.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.WebTeller;
import eu.guy.cucumber.atm.server.ATMServer;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import org.openqa.selenium.OutputType;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceHooks {
    public static int WEBPORT = 9988;
    @Autowired
    public CashSlot slot;
    @Autowired
    private WebTeller teller;
    private ATMServer server;
    private Thread processor;

    //    Annotating feature will automatically run these methods on each scenario
    @Before("@atm-web")
    public void startServer() throws Exception {
        System.out.println("Starting ATM Backend ... port " + WEBPORT);
        server = new ATMServer(WEBPORT, slot);
        server.start();
        teller.openMainPage();
    }

    @After("@atm-web")
    public void stopServer() throws Exception {
        server.stop();
    }

    @Before
    public void startProcessor() {
        processor = TransactionProcessor.getProcessQueueThreaded();
        processor.start();
    }

    @After
    public void stopProcessor() {
        processor.interrupt();
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
