package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.common.DriverFactory;
import eu.guy.cucumber.atm.domain.impl.DirectTeller;
import eu.guy.cucumber.atm.domain.impl.WebTeller;

public class AtmGuiFactory {
    private static boolean shouldBypassGui = false;

    public static AtmGui createGui(CashSlot slot) {
        String override = System.getProperty("test.override");
        if (override != null && override.toLowerCase().contains("no-gui")) {
            return new DirectTeller(slot);
        }
        return new WebTeller(DriverFactory.createChromeDriver());
    }

    public static void bypassGui() {
        shouldBypassGui = true;
    }

    public static void reset() {
        shouldBypassGui = false;
    }
}