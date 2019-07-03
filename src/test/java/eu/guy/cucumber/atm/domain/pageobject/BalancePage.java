package eu.guy.cucumber.atm.domain.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BalancePage extends Page {
    @FindBy(id = "acc-balance")
    private WebElement balance;

    public BalancePage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.titleIs("Account Balance"));
        PageFactory.initElements(driver, this);
    }

    public String getBalance() {
        return balance.getText();
    }
}