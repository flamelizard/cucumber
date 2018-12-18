package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WithdrawalServlet extends HttpServlet {
    private final CashSlot cashSlot;
    private final Account account;

    public WithdrawalServlet(CashSlot cashSlot, Account account) {
        this.cashSlot = cashSlot;
        this.account = account;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
//        value from form -> <input name="amount"
        String amount = req.getParameter("amount");
        Teller teller = new StandardTeller(cashSlot);
        teller.withdrawFrom(account, Money.convert(amount));

        String html;
        if (cashSlot.getContents().getAmount() == 0) {
            html = "<html><body><p>Dispensed $0</p>" +
                    "<p>Sorry, insufficient funds</p></body></html>";
        } else {
            html = "<html><body><p>Dispensed $" +
                    cashSlot.getContents().getAmount() + "</p></body></html>";
        }
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(html);
    }
}
