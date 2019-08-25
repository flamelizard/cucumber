package eu.guy.cucumber.atm.transactions;

import org.javalite.activejdbc.Model;

import java.time.LocalDateTime;

public class Transaction extends Model {
    public Transaction() {
    }

    public int getAccNum() {
        return getInteger("account");
    }

    public double getAmount() {
        return getDouble("amount");
    }

    public String getType() {
        return getString("type");
    }

    public boolean isDebit() {
        return getString("type").equals("-");
    }

    public int getTrnId() {
        return getInteger("id");
    }

    public LocalDateTime getTimestamp() {
        return getTimestamp("timestamp").toLocalDateTime();
    }

    private void setStatus(Status s) {
        setInteger("status", s.getId());
        saveIt();
    }

    public void failed() {
        setStatus(Status.Fail);
    }

    public void completed() {
        setStatus(Status.Completed);
    }
}
