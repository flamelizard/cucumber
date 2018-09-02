package eu.guy.cucumber.atm.utils;

public class Utils {
    public static void sleep(Integer sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
        }
    }
}
