package eu.guy.cucumber.atm.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/*
Feature file and step definitions are expected at the package level of this
Cucumber runner. Alternatively use option "feature"

Feature file is not compiled, thus it has to go to "resources" to get copied to
classpath by maven, else copy explicitly with maven / ant

Cucumber report goes to out/index.html for html:out
 */

// Run as JUnit test through Cucumber runner
// tags = "@debug" / "~@tagToIgnore" to target specific feature / scenario
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:out"}, snippets = SnippetType.CAMELCASE,
        dryRun = false,
        tags = "@debug",
        strict = true,
        glue = "eu.guy.cucumber.atm.step_definitions",
        features = "src/test/java/eu/guy/cucumber/atm/features"
)
public class ATMCukesTest {
}