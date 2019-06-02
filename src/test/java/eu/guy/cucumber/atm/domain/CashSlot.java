package eu.guy.cucumber.atm.domain;

public class CashSlot {
    private Money contents = new Money(0, 0);
    private Money available = new Money(0);

    public Money getContents() {
        return contents;
    }

    public void dispense(Money requested) {
        if (available.compareTo(requested) < 0)
            throw new RuntimeException("Ask for less. ATM funds exceeded");
        available.subtract(requested);
        contents = requested;
    }

    public void load(Money amount) {
//        intentional simple re-set
        available = amount;
    }
}
