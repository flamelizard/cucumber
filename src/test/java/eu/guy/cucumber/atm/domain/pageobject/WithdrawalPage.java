package eu.guy.cucumber.atm.domain.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WithdrawalPage extends Page {
    private String title = "Withdrawal Status";

    public WithdrawalPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.titleIs(title));
    }
}