package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.Instruction;
import eu.guy.cucumber.atm.transactions.TransactionQueue;
import org.javalite.activejdbc.Model;

// ActiveJDBC Model to use Mysql table as an object
public class Account extends Model {
    private TransactionQueue queue = new TransactionQueue();

    //    no-param const must for activeJDBC
    public Account() {
    }

    public Account(int number) {
        setInteger("number", number);
        setString("balance", "0.00");
        saveIt();
    }

    public static Account getAccount(Integer number) {
        return Account.findFirst("number = ?", number);
    }

    public Integer credit(Money money) {
        return queue.write(new Instruction(
                "+", money.getAmount().toString(), getAccountNum()));

    }

    public Integer debit(Money money) {
        return queue.write(new Instruction(
                "-", money.getAmount().toString(), getAccountNum()));
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
