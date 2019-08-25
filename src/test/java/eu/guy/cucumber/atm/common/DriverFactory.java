package eu.guy.cucumber.atm.common;

import eu.guy.cucumber.atm.domain.UatException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileNotFoundException;

// simple browser lifecycle wrapper
// TODO turn to production-grade browser wrapper, parallel test, session reuse etc.
public class DriverFactory {
    private static String CHROME_BINARY = "chromedriver.exe";

    //    TODO suppress driver logging to console
    private static void SetupChromeDriver() {
        String filepath = null;
        try {
            filepath = Common.getFileFromResources(
                    "drivers\\" + CHROME_BINARY).getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new UatException("Webdriver binary not found", e);
        }
        System.setProperty("webdriver.chrome.driver", filepath);
    }

    public static EventFiringWebDriver createChromeDriver() {
        SetupChromeDriver();
        ChromeOptions opts = new ChromeOptions();
        return new EventFiringWebDriver(new ChromeDriver(opts));
    }
}
