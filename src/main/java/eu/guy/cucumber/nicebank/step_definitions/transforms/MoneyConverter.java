package eu.guy.cucumber.nicebank.step_definitions.transforms;

import cucumber.api.Transformer;
import eu.guy.cucumber.nicebank.domain.Money;

/*
Useful when you cannot change class implementation that would let Cucumber
to simply instantiate object
*/
public class MoneyConverter extends Transformer<Money> {
    @Override
    public Money transform(String amount) {
        String[] parts = amount.substring(1).split("\\.");
        Integer dollars = Integer.parseInt(parts[0]);
        Integer cents = Integer.parseInt(parts[1]);
        return new Money(dollars, cents);
    }
}
