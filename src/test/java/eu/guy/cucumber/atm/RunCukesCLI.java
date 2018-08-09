package eu.guy.cucumber.atm;

import cucumber.api.cli.Main;
import org.junit.Test;

import java.nio.file.Paths;

public class RunCukesCLI {
    public static String FEATURE_ROOT = "D:\\projects\\cucumber\\src\\main\\" +
            "java\\eu\\guy\\cucumber\\atm\\features";
    private String stepDefinitions = "eu.guy.cucumber.atm.step_definitions";
    private String FEATURE = "withdrawal.feature";

    @Test
    public void cucumberCLITest() throws Throwable {
        Main.main(new String[]{
                "-p", "progress", "--snippets", "camelcase",
                "--glue", stepDefinitions,
                Paths.get(FEATURE_ROOT).resolve(FEATURE).toString()
        });
    }
}
