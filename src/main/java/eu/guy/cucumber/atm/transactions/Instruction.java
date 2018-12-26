package eu.guy.cucumber.atm.transactions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {
    private static Pattern pattern = Pattern.compile(
            "([+-])([0-9.]+):([0-9]+)");
    private String type;
    private String amount;
    private Integer id;
    private Integer accNumber;

    public Instruction(String type, String amount, Integer account) {
        this.type = type;
        this.amount = amount;
        this.accNumber = account;
    }

    public static Instruction parseFrom(String message, Integer id) {
        Matcher match = pattern.matcher(message);
        if (match.find()) {
            return new Instruction(match.group(1),
                    match.group(2), Integer.valueOf(match.group(3)))
                    .setId(id);
        }
        return null;
    }

    public Integer getAccNumber() {
        return accNumber;
    }

    public String toTransfer() {
        return String.format("%s%s:%d", type, amount, accNumber);
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

    public String toString() {
        return String.format("[%d] acc:%d %s%s", id, accNumber, type, amount);
    }

    public Instruction setId(Integer id) {
        this.id = id;
        return this;
    }
}
