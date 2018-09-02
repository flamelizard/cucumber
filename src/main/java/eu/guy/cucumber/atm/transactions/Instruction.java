package eu.guy.cucumber.atm.transactions;

public class Instruction {
    private String type;
    private String amount;

    public Instruction(String message) {
//        minor sanity check, goal here is not bulletproof code
        if (message.matches("[+-][0-9.]+")) {
            type = String.valueOf(message.charAt(0));
            amount = message.substring(1);
        }
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }
}
