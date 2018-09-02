package eu.guy.cucumber.atm.domain;

public class StandardTeller implements Teller {
    private CashSlot cashSlot;

    public StandardTeller(CashSlot cashSlot) {
        this.cashSlot = cashSlot;
    }

    public void withdrawFrom(Account account, Money amount) {
        account.debit(amount);
//        TODO - dispense should occur after transaction
        cashSlot.dispense(amount);
    }
}
