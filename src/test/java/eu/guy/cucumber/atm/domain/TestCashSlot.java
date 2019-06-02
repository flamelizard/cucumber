package eu.guy.cucumber.atm.domain;

public class TestCashSlot extends CashSlot {
    private boolean isFaulty = false;

    public TestCashSlot() {
        load(new Money(1000000000));
    }

    public void injectFault() {
        isFaulty = true;
    }

    @Override
    public void dispense(Money requested) {
        if (isFaulty)
            throw new RuntimeException("Out of order");
        super.dispense(requested);
    }
}
