package eu.guy.cucumber.nicebank;

import cucumber.api.cli.Main;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * Created by Tom on 11/15/2017.
 */
public class RunCukesCLI {
    public static String FEATURE_ROOT = "D:\\projects\\cucumber\\src\\main\\" +
            "java\\eu\\guy\\cucumber\\nicebank\\features";
    private String stepDefinitions = "eu.guy.cucumber.nicebank.step_definitions";
    private String FEATURE = "atm.feature";

    @Test
    public void cucumberCLITest() throws Throwable {
        Main.main(new String[]{
                "-p", "progress", "--snippets", "camelcase",
                "--glue", stepDefinitions,
                Paths.get(FEATURE_ROOT).resolve(FEATURE).toString()
        });
    }
}
