package eu.guy.cucumber.atm.domain;

public class BusinessException extends Exception {
    public BusinessException(String s) {
        super(s);
    }
}
