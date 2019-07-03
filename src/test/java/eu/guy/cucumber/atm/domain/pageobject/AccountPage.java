package eu.guy.cucumber.atm.domain.pageobject;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.Money;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountPage extends Page {
    @FindBy(id = "balance")
    private WebElement balanceForm;

    @FindBy(css = "form#withdraw input[name='account']")
    private WebElement accountWithdraw;

    @FindBy(css = "input[value][name='amount']")
    private List<WebElement> amountRadios;

    @FindBy(css = "input[type='text'][name='amount']")
    private WebElement withdrawAmount;

    @FindBy(id = "btn-withdraw")
    private WebElement withdraw;

    @FindBy(css = "#balance input[name='account']")
    private WebElement accountBalance;

    @FindBy(id = "btn-balance")
    private WebElement queryBalance;

    public AccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(balanceForm));
    }

    public WithdrawalPage withdrawFrom(Account acc, String amount) {
        Optional<WebElement> radio = amountRadios.stream()
                .filter(input -> input.getAttribute("value").equals(amount))
                .findFirst();
        if (!radio.isPresent()) {
            List<String> amounts = amountRadios.stream()
                    .map(e -> e.getAttribute("value"))
                    .filter(val -> val.matches("[0-9]+"))
                    .collect(Collectors.toList());
            throw new RuntimeException("Amount not found. Available: " + amounts);
        }

        typeAccount(acc);
        radio.get().click();
        withdraw.click();
        return new WithdrawalPage(driver);
    }

    public BalancePage checkBalance(Account acc) {
        accountBalance.sendKeys(acc.getAccountNum().toString());
        queryBalance.click();
        return new BalancePage(driver);
    }

    public AccountPage typeAccount(Account acc) {
        accountWithdraw.sendKeys(acc.getAccountNum().toString());
        return this;
    }

    public AccountPage typeCustomWithdrawal(Money amount) {
        withdrawAmount.sendKeys(amount.getAmount().toString());
        return this;
    }
}
