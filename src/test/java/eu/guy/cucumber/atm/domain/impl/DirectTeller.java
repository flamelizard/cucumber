package eu.guy.cucumber.atm.domain.impl;

import eu.guy.cucumber.atm.domain.*;

public class DirectTeller implements AtmGui {
    private Teller teller;

    public DirectTeller(CashSlot slot) {
        teller = new StandardTeller(slot);
    }

    public void open() {
    }

    public void close() {
    }

    public void fillWithdrawAmount(Account acc, Money amount) {
    }

    public Money checkBalance(Account acc) {
        return acc.getBalance();
    }

    public boolean isDisplaying(String text) {
        return false;
    }

    public void withdrawFrom(Account acc, Money amount) {
        teller.withdrawFrom(acc, amount);
    }
}