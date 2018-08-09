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

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToMain() {
        webDriver.get("http://localhost:" + ServiceHooks.WEBPORT);
    }

    @Override
    public void withdrawFrom(Account account, Money money) {
//      select from radio buttons of fixed value
        String xpath = String.format(
                "//input[@value='%d']", money.getAmount().intValue());
        webDriver.findElement(By.xpath(xpath)).click();
        webDriver.findElement(By.id("withdraw")).click();
    }

    public String getCurrentScreenHtml() {
        return webDriver.getPageSource();
    }

    public boolean accountHasInsufficientFunds() {
        return webDriver.getPageSource().contains("insufficient funds");
    }

    public void checkBalance() {
        webDriver.findElement(By.id("btn-balance")).click();
    }

    public Money getDisplayedBalance() {
        String balance = webDriver.findElement(
                By.id("balance")).getText();
        return Money.convert(balance);
    }
}
