package eu.guy.cucumber.atm.domain;

public class CashSlot {
    private Money contents = new Money(0, 0);
    private Money available = new Money(0);

    public Money getContents() {
        return contents;
    }

    public void dispense(Money requested) {
        if (!canDispense(requested))
            throw new RuntimeException("Ask for less. ATM funds exceeded");
        available.subtract(requested);
        contents = requested;
    }

    public void load(Money amount) {
//        intentional simple re-set
        available = amount;
    }

    public boolean canDispense(Money amount) {
        return available.compareTo(amount) >= 0;
    }
}
