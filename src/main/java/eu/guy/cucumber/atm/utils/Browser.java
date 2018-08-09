package eu.guy.cucumber.atm.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class Browser {
    public static String DRIVER =
            "D:\\projects\\cucumber\\src\\test\\resources\\chromedriver.exe";
    private EventFiringWebDriver webDriver;

    public Browser() {
        System.setProperty("webdriver.chrome.driver", DRIVER);
    }

    public EventFiringWebDriver getWebDriver() {
        if (webDriver == null)
            webDriver = new EventFiringWebDriver(new ChromeDriver());
        return webDriver;
    }
}
