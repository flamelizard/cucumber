package eu.guy.cucumber.atm.domain;

public class UatException extends RuntimeException {

    public UatException(String msg) {
        super(msg);
    }

    public UatException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
