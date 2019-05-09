package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.TransactionHandler;
import eu.guy.cucumber.atm.transactions.TransactionQueue;
import org.javalite.activejdbc.Model;

// ActiveJDBC Model to use Mysql table as an object
public class Account extends Model {
    private TransactionQueue queue = new TransactionQueue();
    private TransactionHandler handler = new TransactionHandler();

    //    no-param const must for activeJDBC
    public Account() {
    }

    private Account(int number) {
        setInteger("number", number);
        setString("balance", "0.00");
        saveIt();
    }

    public static Account createAccount(int number) {
        return new Account(number);
    }

    public static Account getAccountOrNull(Integer number) {
        return Account.findFirst("number = ?", number);
    }

    public Integer credit(Money money) {
        return TransactionHandler.writeTrn(
                getAccountNum(), "+", money.getAmount().intValue()
        );
    }

    public Integer debit(Money money) {
        return TransactionHandler.writeTrn(
                getAccountNum(), "-", money.getAmount().intValue()
        );
    }

    public Money getBalance() {
//        necessary for multiple db connections
        refresh();
        return Money.convert(getString("balance"));
    }

    public void setBalance(Money amount) {
        setString("balance", amount.getAmount());
        saveIt();
    }

    public Integer getAccountNum() {
        return getInteger("number");
    }
}
