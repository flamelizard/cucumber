package eu.guy.cucumber.atm.transactions;

import eu.guy.cucumber.atm.transactions.events.EventLogger;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class TransactionHandler {
    static DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;

    private static Transaction write(int account, String type, double amount) {
        return Transaction.createIt(
                "account", account, "type", type,
                "amount", amount, "status", Status.Waiting.getId(),
                "timestamp", dtf.format(LocalDateTime.now()));
    }

    public static int writeTrn(int account, String type, double amount) {
        Transaction t = write(account, type, amount);
        return t.getTrnId();
    }

    public static Transaction getPending() {
        List<Transaction> pending = Transaction
                .where("status = " + Status.Waiting.getId())
                .orderBy("id asc");
        if (pending.size() > 0)
            return pending.get(0);
        return null;
    }

    //    TODO fun, parsing / comparing timestamp
    public static List<Transaction> getFromDate(Date d) {
        throw new NotImplementedException("Implement me");
    }

    public static Transaction readById(int trnId) {
        return Transaction.findFirst("id = '" + trnId + "'");
    }

    //    dead simple - generate counter transaction where transaction type is flipped
    public static void resetTrn(int trnId) {
        Transaction trn = readById(trnId);
        String type = trn.isDebit() ? "+" : "-";
        int id = writeTrn(trn.getAccNum(), type, trn.getAmount());
        EventLogger.waitForEvent(id, 5);
    }
}
