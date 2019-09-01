package eu.guy.cucumber.atm.common;

import eu.guy.cucumber.atm.domain.UatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

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

    public static EventFiringWebDriver getDriverInstance() {
        String isRemoteRun = System.getProperty("webdriver.remote");
        if (isRemoteRun != null) {
            System.out.println("Getting remote webdriver... ");
            return new EventFiringWebDriver(getRemoteChrome());
        }
        return new EventFiringWebDriver(getChrome());
    }

    private static WebDriver getChrome() {
        SetupChromeDriver();
        ChromeOptions opts = new ChromeOptions();
        return new ChromeDriver(opts);
    }

    private static WebDriver getRemoteChrome() {
        try {
            return new RemoteWebDriver(
                    new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
        } catch (MalformedURLException e) {
            throw new UatException("Could not create remote webdriver", e);
        }
    }
}
