package eu.guy.cucumber.atm.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMoneyToFloat() throws BusinessException {
        Money d = new Money(0, 60);
        assertEquals(d.getAmount(), 0.6f, 0);

        d = new Money(150, 85);
        assertEquals(d.getAmount(), 150.85f, 0);
    }

    @Test
    public void testSubtraction() throws BusinessException {
        Money d = new Money(10, 90);
        d.subtract(new Money(10, 90));
        assertEquals(d, new Money(0, 0));

        d = new Money(10, 20);
        d.subtract(new Money(2, 70));
        assertEquals(d, new Money(7, 50));

        d = new Money(99, 90);
        d.subtract(new Money(0, 90));
        assertEquals(d, new Money(99, 0));

        d = new Money(0, 0);
        d.subtract(new Money(0, 0));
        assertEquals(d, new Money(0, 0));

        thrown.expect(BusinessException.class);
        d = new Money(0, 50);
        d.subtract(new Money(0, 90));

        thrown.expect(BusinessException.class);
        d = new Money(100, 0);
        d.subtract(new Money(101, 0));
    }

    @Test
    public void testAddition() throws BusinessException {
        Money d = new Money(100, 20);
        d.add(new Money(100, 30));
        assertEquals(d, new Money(200, 50));

        d = new Money(50, 10);
        d.add(new Money(10, 90));
        assertEquals(d, new Money(61, 0));

        d = new Money(5, 80);
        d.add(new Money(10, 50));
        assertEquals(d, new Money(16, 30));
    }

    @Test
    public void testConversionFromDecimal() {
        assertEquals(Money.convert("1.5"), new Money(1, 50));
        assertEquals(Money.convert("10.99"), new Money(10, 99));
        assertEquals(Money.convert("5.01"), new Money(5, 1));

        thrown.expect(AssertionError.class);
        assertEquals(Money.convert("5.999"), new Money(0, 0));
    }
}