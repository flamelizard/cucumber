package eu.guy.cucumber.atm.domain;

public class Money {
    public static int CENTPERDOLLAR = 100;
    private int dollar;
    private int cents;

    public Money(int dollar, int cents) {
        this.dollar = dollar;
        this.cents = cents;
        assert cents < CENTPERDOLLAR;
    }

    //  e.g $1.5 = $1 + 50c
    public static Money convert(String amount) {
        if (amount == null || amount.equals("")) return null;
        String[] decimal = amount.trim().replace("$", "").split("\\.");
        int cent = 0;
        int dollar = Integer.valueOf(decimal[0]);
        if (decimal.length > 1)
            cent = Math.round(Float.valueOf("0." + decimal[1]) * CENTPERDOLLAR);
        return new Money(dollar, cent);
    }

    public Float getAmount() {
        float amount = dollar + (float) cents / CENTPERDOLLAR;
        String rounded = String.format("%.2f", amount);
        return Float.valueOf(rounded);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Money))
            return false;
        return this.getAmount().equals(((Money) that).getAmount());
    }

    public void subtract(Money amount) throws BusinessException {
        int c = this.cents - amount.cents;
        int d = this.dollar - amount.dollar;
        if (c < 0) {
            d += -1;
            c = CENTPERDOLLAR + c;
        }
        if (d < 0)
            throw new BusinessException("Result dollars cannot be negative");
        this.dollar = d;
        this.cents = c;
    }

    public void add(Money amount) {
        int d = this.dollar + amount.dollar;
        int c = this.cents + amount.cents;
        if (c >= 100) {
            c -= CENTPERDOLLAR;
            d += 1;
        }
        this.dollar = d;
        this.cents = c;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", dollar, cents);
    }
}
