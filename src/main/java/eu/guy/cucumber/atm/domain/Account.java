package eu.guy.cucumber.atm.domain;

public class Account {

    private Money balance;

    public Account() {
        balance = new Money(0, 0);
    }

    public void credit(Money money) {
        balance.add(money);
    }

    public Money getBalance() {
        return balance;
    }

    public boolean debit(Money money) {
        try {
            getBalance().subtract(money);
        } catch (BusinessException e) {
            return false;
        }
        return true;
    }
}
