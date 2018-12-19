package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.TransactionQueue;
import org.javalite.activejdbc.Model;

public class Account extends Model {
    private TransactionQueue queue = new TransactionQueue();

    public Integer credit(Money money) {
        return queue.write("+" + money.getAmount().toString() +
                "," + getAccountNum());
    }

    public Integer debit(Money money) {
        return queue.write("-" + money.getAmount().toString() +
                "," + getAccountNum());
    }

    public Money getBalance() {
        return Money.convert(getString("balance"));
    }

    public void setBalance(Money amount) {
//        operates directly on db managed by activeJDBC
        setString("balance", amount.getAmount());
        saveIt();
    }

    private Integer getAccountNum() {
        return getInteger("number");
    }
}
