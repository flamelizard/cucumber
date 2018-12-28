package eu.guy.cucumber.atm;

import eu.guy.cucumber.atm.utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// TODO try refactor to have common entry point, without using cucumber hooks
public class AppSetup {
    public static String projectFile = "project.properties";
    private static Properties project;

    static {
//        FIXME not good solution
        try {
            FileInputStream fis = new FileInputStream(Utils.getProjectFile(projectFile));
            project = new Properties();
            project.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProjectProperty(String key) {
        return project.getProperty(key);
    }
}