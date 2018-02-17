package eu.guy.cucumber.nicebank;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/*
Looks for feature file at the same location on classpath by default
Or use feature option in annotation

Feature file is not compiled, it has to go to "resources" to get copied to
classpath by maven, else copy explicitly with maven
 */

// Run as JUnit test through Cucumber runner
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "pretty", snippets = SnippetType.CAMELCASE,
        dryRun = false)
public class RunCukes {
}

// NEXT UP - done meet-greet DI
