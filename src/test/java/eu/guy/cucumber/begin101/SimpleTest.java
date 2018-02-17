package eu.guy.cucumber.begin101;

import cucumber.api.cli.Main;
import org.junit.Test;

/**
 * Created by Tom on 11/15/2017.
 */
public class SimpleTest {
    private String feature = "D:\\projects\\cucumber\\src\\main\\java\\eu\\" +
            "guy\\cucumber\\begin101\\features\\checkout.feature";

    private String stepDefinitions =
            "eu.guy.cucumber.begin101.step_definitions";

    @Test
    public void cucumberCLITest() throws Throwable {
        Main.main(new String[]{
                "-p", "progress", "--snippets", "camelcase",
                "--glue", stepDefinitions, feature});
    }
}
