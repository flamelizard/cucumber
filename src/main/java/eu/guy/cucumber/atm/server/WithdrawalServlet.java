package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

public class WithdrawalServlet extends HttpServlet {
    private final CashSlot cashSlot;
    private String htmlFmt = "<html>" +
            "<head><title>Withdrawal Status</title></head>" +
            "<body>%s</body>" +
            "</html>";
    private String body;

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
            body = "<p>" + ex.getMessage() + "</p>";
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(format(htmlFmt, body));
            return;
        }

        body = "<p>Dispensed $" + cashSlot.getContents().getAmount() + "</p>";
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(format(htmlFmt, body));
    }
}