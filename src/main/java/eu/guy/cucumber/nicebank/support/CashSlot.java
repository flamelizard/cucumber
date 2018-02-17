package eu.guy.cucumber.nicebank.support;

/**
 * Created by Tom on 2/14/2018.
 */
public class CashSlot {
    private int contents;

    public int getContents() {
        return contents;
    }

    public void dispense(int dollars) {
        contents = dollars;
    }
}
