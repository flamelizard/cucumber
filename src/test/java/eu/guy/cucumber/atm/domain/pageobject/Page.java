package eu.guy.cucumber.atm.domain.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

class Page {
    protected WebDriverWait wait;
    protected WebDriver driver;

    Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 3);
    }
}