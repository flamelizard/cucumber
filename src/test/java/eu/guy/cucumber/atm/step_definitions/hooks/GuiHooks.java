package eu.guy.cucumber.atm.step_definitions.hooks;

import cucumber.api.java.Before;
import eu.guy.cucumber.atm.domain.AtmGuiFactory;

public class GuiHooks {
    @Before(value = "@bypass-ui", order = 1)
    public void doNotUseWebGui() {
        AtmGuiFactory.bypassGui();
    }
}