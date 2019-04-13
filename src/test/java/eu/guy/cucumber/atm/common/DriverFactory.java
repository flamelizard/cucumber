package eu.guy.cucumber.atm.common;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileNotFoundException;

// simple browser lifecycle wrapper
public class DriverFactory {
    private static String CHROME_EXE = "chromedriver.exe";

    //    TODO suppress driver logging to console
    private static void SetupChromeDriver() {
        String filepath = null;
        try {
            filepath = Utils.getProjectFile(CHROME_EXE).getAbsolutePath();
        } catch (FileNotFoundException e) {
            System.out.println("Error: driver executable not found");
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", filepath);
    }

    public static EventFiringWebDriver createChromeDriver() {
        SetupChromeDriver();
        return new EventFiringWebDriver(new ChromeDriver());
    }
}