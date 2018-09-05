package eu.guy.cucumber.begin101;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;


/*
It is possible to run with cucumber.api.cli.Main for quick time check.
However maven test fails such test as it always terminates with System.exit()
 */
@RunWith(Cucumber.class)
@Ignore
@CucumberOptions(
        glue = "eu.guy.cucumber.begin101.step_definitions",
        features = "src\\main\\java\\eu\\guy\\cucumber\\begin101\\features" +
                "\\checkout.feature",
        plugin = {"progress"}, snippets = SnippetType.CAMELCASE)
public class CheckoutTest {
}
