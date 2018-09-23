package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.BalanceStore;
import eu.guy.cucumber.atm.transactions.TransactionQueue;

import java.io.FileNotFoundException;

public class Account {
    private TransactionQueue queue = new TransactionQueue();

    public Account() {
    }

    public Integer credit(Money money) {
        return queue.write("+" + money.getAmount().toString());
    }

    public Integer debit(Money money) {
        return queue.write("-" + money.getAmount().toString());
    }

    public Money getBalance() {
        try {
            return BalanceStore.getBalance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
