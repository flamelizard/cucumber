package eu.guy.cucumber.nicebank.domain;

/**
 * Created by Tom on 2/11/2018.
 */
public class Money {
    private int dollars;
    private int cents;

    public Money(int dollars, int cents) {

        this.dollars = dollars;
        this.cents = cents;
    }

    public Money() {
    }

    public void add(Money money) {
        dollars += money.dollars;
        cents += money.cents;
    }

    @Override
    public boolean equals(Object other) {
        Money otherMoney = (Money) other;
        return dollars == otherMoney.dollars && cents == otherMoney.cents;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", dollars, cents);
    }
}
