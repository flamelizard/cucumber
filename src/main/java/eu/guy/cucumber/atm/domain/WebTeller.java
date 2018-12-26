package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.domain.hooks.ServiceHooks;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class WebTeller implements Teller {
    private EventFiringWebDriver webDriver;

    public WebTeller(EventFiringWebDriver webDriver) {
        this.webDriver = webDriver;
        goToMain();
    }

    public void goToMain() {
        webDriver.get("http://localhost:" + ServiceHooks.WEBPORT);
    }

    //    TODO may refactor to page object
    @Override
    public void withdrawFrom(Account account, Money money) {
        webDriver.findElement(By.cssSelector(
                "form#withdraw input[name='account']"))
                .sendKeys(String.valueOf(account.getAccountNum()));

//      select from radio buttons of fixed value
        String xpath = String.format(
                "//input[@value='%d']", money.getAmount().intValue());
        webDriver.findElement(By.xpath(xpath)).click();
        webDriver.findElement(By.id("btn-withdraw")).click();
    }

    public boolean accountHasInsufficientFunds() {
        return webDriver.getPageSource().contains("insufficient funds");
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
}
