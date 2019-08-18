package eu.guy.cucumber.atm.domain.impl;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.AtmGui;
import eu.guy.cucumber.atm.domain.Money;
import eu.guy.cucumber.atm.domain.pageobject.AccountPage;
import eu.guy.cucumber.atm.domain.pageobject.BalancePage;
import eu.guy.cucumber.atm.step_definitions.hooks.ServiceHooks;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;

public class WebTeller implements AtmGui {
    private EventFiringWebDriver webDriver;
    private BalancePage balancePage;

    public WebTeller(EventFiringWebDriver driver) {
        this.webDriver = driver;
    }

    public void open() {
        webDriver.get("http://localhost:" + ServiceHooks.WEBPORT);
    }

    public void close() {
        if (webDriver != null) {
            webDriver.close();
            webDriver.quit();
        }
    }

    private AccountPage getMainPage() {
        return new AccountPage(webDriver);
    }

    public void withdrawFrom(Account account, Money money) {
        String amount = String.valueOf(money.getAmount().intValue());
        getMainPage().withdrawFrom(account, amount);
    }

    public void fillWithdrawAmount(Account account, Money amount) {
        getMainPage()
                .typeAccount(account)
                .typeCustomWithdrawal(amount);
    }

    public Money checkBalance(Account account) {
        balancePage = getMainPage().checkBalance(account);
        return Money.convert(balancePage.getBalance());
    }

    public EventFiringWebDriver getWebDriver() {
        return webDriver;
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
