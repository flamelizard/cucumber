package eu.guy.cucumber.atm.step_definitions;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import eu.guy.cucumber.atm.domain.Money;
import io.cucumber.cucumberexpressions.ParameterType;

import java.util.Locale;

// Cucumber will instantiate automatically on glue path
public class GherkinCustomTypes implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        registry.defineParameterType(new ParameterType<>(
                "money",
                "[$][0-9.]+",
                Money.class,
                Money::convert
        ));
    }
}
