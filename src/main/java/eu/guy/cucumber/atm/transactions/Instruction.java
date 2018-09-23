package eu.guy.cucumber.atm.transactions;

public class Instruction {
    private String type;
    private String amount;
    private Integer id;

    public Instruction(String message, Integer id) {
//        minor sanity check only
        if (message.matches("[+-][0-9.]+")) {
            type = String.valueOf(message.charAt(0));
            amount = message.substring(1);
        }
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public Integer getId() {
        return id;
    }
}
