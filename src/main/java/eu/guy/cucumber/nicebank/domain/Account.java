package eu.guy.cucumber.nicebank.domain;

/**
 * Created by Tom on 2/11/2018.
 */
public class Account {

    private Money balance;

    public Account() {
        balance = new Money();
    }

    public void deposit(Money money) {
        balance.add(money);
    }

    public Money getBalance() {
        return balance;
    }
}
