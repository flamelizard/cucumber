package eu.guy.cucumber.atm.domain;

import eu.guy.cucumber.atm.utils.Browser;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileNotFoundException;

public class KnowsTheDomain {
    private Account account;
    private CashSlot cashSlot;
    private Teller teller;
    private Browser browserFactory;
    private Integer TEST_ACC_NUM = 100;

    public KnowsTheDomain() throws FileNotFoundException {
        browserFactory = new Browser();
    }

    public Account getMyAccount() {
//    no stinking null instance var in test class, tough to debug problems later
        if (account == null)
            account = new Account(TEST_ACC_NUM);
        return account;
    }

    public Teller getTeller() {
        if (teller == null) {
//            teller = new StandardTeller(getCashSlot());
            teller = new WebTeller(getWebDriver());
        }
        return teller;
    }

    public CashSlot getCashSlot() {
        if (cashSlot == null)
            cashSlot = new CashSlot();
        return cashSlot;
    }

    public EventFiringWebDriver getWebDriver() {
        return browserFactory.getWebDriver();
    }
}
