package eu.guy.cucumber.atm.step_definitions.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import eu.guy.cucumber.atm.domain.AtmGui;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.impl.WebTeller;
import eu.guy.cucumber.atm.server.ATMServer;
import eu.guy.cucumber.atm.transactions.TransactionProcessor;
import org.openqa.selenium.OutputType;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceHooks {
    public static int WEBPORT = 9988;
    @Autowired
    private CashSlot slot;
    @Autowired
    private AtmGui teller;
    private ATMServer server;
    private TransactionProcessor processor;

    @Before("@atm-web")
    public void startServer() throws Exception {
        System.out.println("Starting ATM Backend ... port " + WEBPORT);
        server = new ATMServer(WEBPORT, slot);
        server.start();
        teller.open();
    }

    @After("@atm-web")
    public void stopServer() throws Exception {
        server.stop();
    }

    @Before
    public void startProcessor() {
        processor = new TransactionProcessor();
        processor.go();
    }

    @After
    public void stopProcessor() {
        processor.quit();
    }

    @After
    public void cleanUp(Scenario scenario) {
        if (scenario.isFailed() && teller instanceof WebTeller) {
            byte[] screenshot = ((WebTeller) teller).getWebDriver().getScreenshotAs(OutputType.BYTES);
//            include screenshot in html report
            scenario.embed(screenshot, "image/png");
        }
    }
}
