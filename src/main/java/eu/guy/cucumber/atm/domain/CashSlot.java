package eu.guy.cucumber.atm.domain;

public class CashSlot {
    private Money contents = new Money(0, 0);

    public Money getContents() {
        return contents;
    }

    public void dispense(Money money) {
        contents = money;
    }
}
