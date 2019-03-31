package eu.guy.cucumber.atm.domain;

// Cannot extend Account, ActiveJDBC instrumentation breaks
public class TestAccount {
    private Account account;

    public TestAccount() {
        account = new Account(123);
    }

    public Account getAccount() {
        return account;
    }
}
