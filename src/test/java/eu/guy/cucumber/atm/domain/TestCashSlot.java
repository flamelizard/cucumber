package eu.guy.cucumber.atm.domain;

public class TestCashSlot extends CashSlot {
    private boolean isFaulty = false;

    public void injectFault() {
        isFaulty = true;
    }

    @Override
    public void dispense(Money money) {
        if (isFaulty)
            throw new RuntimeException("Out of order");
        super.dispense(money);
    }
}
