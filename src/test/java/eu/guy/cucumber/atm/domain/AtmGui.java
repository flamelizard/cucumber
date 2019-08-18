package eu.guy.cucumber.atm.domain;

public interface AtmGui extends Teller {
    void open();

    void close();

    void fillWithdrawAmount(Account acc, Money amount);

    Money checkBalance(Account acc);

    boolean isDisplaying(String text);
}