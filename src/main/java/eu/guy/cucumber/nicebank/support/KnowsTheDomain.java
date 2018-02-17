package eu.guy.cucumber.nicebank.support;

/**
 * Created by Tom on 2/14/2018.
 */
//    no stinking null instance var in test class, tough to debug problems later
public class KnowsTheDomain {
    private Account account;
    private CashSlot cashSlot;
    private Teller teller;

    public Account getMyAccount() {
        if (account == null)
            account = new Account();
        return account;
    }

    public Teller getTeller() {
        if (teller == null)
            teller = new Teller(getCashSlot());
        return teller;
    }

    public CashSlot getCashSlot() {
        if (cashSlot == null)
            cashSlot = new CashSlot();
        return cashSlot;
    }
}
