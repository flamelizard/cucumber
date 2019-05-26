package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WithdrawalServlet extends HttpServlet {
    private final CashSlot cashSlot;

    public WithdrawalServlet(CashSlot cashSlot) {
        this.cashSlot = cashSlot;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
//        value from form -> <input name="amount"
        String amount = req.getParameter("amount");
        Integer accNumber = Integer.valueOf(req.getParameter("account"));

        DataStore.createConnection();
        Account account = Account.getAccountOrNull(accNumber);

        Teller teller = new StandardTeller(cashSlot);
        try {
            teller.withdrawFrom(account, Money.convert(amount));
        } catch (RuntimeException ex) {
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(
                    "<html><title>ATM</title><body><p>"
                            + ex.getMessage()
                            + "</p></body></html>");
            return;
        }

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
