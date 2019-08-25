package eu.guy.cucumber.atm.transactions;

import java.util.ArrayList;
import java.util.List;

public class WaitingTrans {
    private List<Transaction> trans = new ArrayList<>();
    private String clause;
    private int nextTrnId = 0;  // the greatest trn id from last query

    public List<Transaction> get() {
        clause = String.format("status = %s and id >= %d",
                Status.Waiting.getId(), nextTrnId);
        trans = Transaction.where(clause).orderBy("id asc");
        if (trans.size() == 0)
            return new ArrayList<>();
        nextTrnId = trans.get(trans.size() - 1).getTrnId() + 1;
        return trans;
    }
}
