package eu.guy.cucumber.atm.transactions.events;

import java.util.StringJoiner;

import static java.lang.String.format;


public class Event {
    private StringJoiner message;
    private Integer trnId;
    private String type;

    public Event(String type, Integer trnId) {
        this.type = type;
        this.trnId = trnId;
        message = new StringJoiner(" ");
    }

    public Event add(String key, String value) {
        value = value.contains("=") ? value.replace("=", "_") : value;
        message.add(format("%s=<%s>", key, value));
        return this;
    }

    public String toString() {
        return format("id=<%s> type=<%s> %s",
                trnId, type, message.toString());
    }
}
