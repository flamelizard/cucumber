package eu.guy.cucumber.atm.step_definitions.hooks;

import cucumber.api.java.Before;
import eu.guy.cucumber.atm.server.DataStore;

public class DbHooks {
    @Before(order = 0)
    public void setUp() {
        DataStore.createConnection();
        DataStore.deleteTransactions();
        DataStore.deleteAccounts();
    }
}
