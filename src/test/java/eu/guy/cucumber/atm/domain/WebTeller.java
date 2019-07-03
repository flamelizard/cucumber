package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.domain.pageobject.AccountPage;
import eu.guy.cucumber.atm.domain.pageobject.BalancePage;
import eu.guy.cucumber.atm.step_definitions.hooks.ServiceHooks;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;

public class WebTeller implements Teller {
    private EventFiringWebDriver webDriver;
    private BalancePage balancePage;

    public WebTeller(EventFiringWebDriver driver) {
        this.webDriver = driver;
    }

    public void openMainPage() {
        webDriver.get("http://localhost:" + ServiceHooks.WEBPORT);
    }

    private AccountPage getMainPage() {
        return new AccountPage(webDriver);
    }

    @Override
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
