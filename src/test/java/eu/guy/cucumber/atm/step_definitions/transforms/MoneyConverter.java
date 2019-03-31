package eu.guy.cucumber.atm.step_definitions.transforms;

import cucumber.api.Transformer;
import eu.guy.cucumber.atm.domain.Money;

/*
Useful when I have no access to class implementation
that would let Cucumber to simply instantiate object via constructor
*/
public class MoneyConverter extends Transformer<Money> {
    @Override
    public Money transform(String amount) {
        return Money.convert(amount);
    }
}
