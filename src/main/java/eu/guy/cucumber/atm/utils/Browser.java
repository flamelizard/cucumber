package eu.guy.cucumber.atm.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileNotFoundException;

// simple browser lifecycle wrapper
public class Browser {
    private static String DRIVER_EXE = "chromedriver.exe";
    private EventFiringWebDriver webDriver;

    public Browser() throws FileNotFoundException {
        String filepath = Utils.getProjectFile(DRIVER_EXE).getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", filepath);
    }

    public EventFiringWebDriver getWebDriver() {
        if (webDriver == null)
            webDriver = new EventFiringWebDriver(new ChromeDriver());
        return webDriver;
    }
}
