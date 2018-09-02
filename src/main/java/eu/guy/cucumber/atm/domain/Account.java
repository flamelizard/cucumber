package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.transactions.BalanceStore;
import eu.guy.cucumber.atm.transactions.TransactionQueue;

import java.io.FileNotFoundException;

public class Account {
    private TransactionQueue queue = new TransactionQueue();

    public Account() {
    }

    public void credit(Money money) {
        queue.write("+" + money.getAmount().toString());
    }

    public void debit(Money money) {
        queue.write("-" + money.getAmount().toString());
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
