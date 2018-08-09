package eu.guy.cucumber.atm.domain;

public interface Teller {
    void withdrawFrom(Account account, Money amount);
}
