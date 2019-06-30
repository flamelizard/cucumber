package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.step_definitions.hooks.ServiceHooks;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;

public class WebTeller implements Teller {
    private EventFiringWebDriver webDriver;

    public WebTeller(EventFiringWebDriver driver) {
        this.webDriver = driver;
    }

    public void openMainPage() {
        webDriver.get("http://localhost:" + ServiceHooks.WEBPORT);
    }

    //    TODO may refactor to page object
    @Override
    public void withdrawFrom(Account account, Money money) {
//        TODO, practice, check first available values to select
        webDriver.findElement(By.cssSelector(
                "form#withdraw input[name='account']"))
                .sendKeys(String.valueOf(account.getAccountNum()));

//      select from radio buttons of fixed value
        String xpath = format(
                "//input[@value='%d']", money.getAmount().intValue());
        webDriver.findElement(By.xpath(xpath)).click();
        webDriver.findElement(By.id("btn-withdraw")).click();
    }

    private void enterAccount(Account account) {
        webDriver.findElement(By.cssSelector(
                "form#withdraw input[name='account']"))
                .sendKeys(String.valueOf(account.getAccountNum()));
    }

    private void enterWithdrawal(Money amount) {
        webDriver.findElement(By.cssSelector(
                "input[type='text'][name='amount']"))
                .sendKeys(amount.getAmount().toString());
    }

    public void fillwithdrawal(Account account, Money amount) {
        enterAccount(account);
        enterWithdrawal(amount);
    }

    public void checkBalance(Account account) {
        webDriver.findElement(By.cssSelector(
                "form#balance input[name='account']"))
                .sendKeys(String.valueOf(account.getAccountNum()));
        webDriver.findElement(By.id("btn-balance")).click();
    }

    public Money getDisplayedBalance() {
        String balance = webDriver.findElement(
                By.id("acc-balance")).getText();
        return Money.convert(balance);
    }

    public EventFiringWebDriver getWebDriver() {
        return webDriver;
    }

    public void close() {
        webDriver.close();
        webDriver.quit();
    }

    public boolean isDisplaying(String text) {
        By xpath = By.xpath(format("//*[contains(text(),\"%s\")]", text));
        try {
            new WebDriverWait(webDriver, 3)
                    .until(ExpectedConditions.presenceOfElementLocated(xpath));
        } catch (TimeoutException ex) {
            return false;
        }
        return true;
    }

}
