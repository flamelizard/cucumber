package eu.guy.cucumber.nicebank.domain;

/**
 * Created by Tom on 2/14/2018.
 */
public class Teller {
    private CashSlot cashSlot;

    public Teller(CashSlot cashSlot) {
        this.cashSlot = cashSlot;
    }

    public void withdrawFrom(Account account, int amount) {
        cashSlot.dispense(amount);
    }
}
