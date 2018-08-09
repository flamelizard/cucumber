package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.Account;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BalanceServlet extends HttpServlet {
    private Account account;

    public BalanceServlet(Account account) {
        this.account = account;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String html = "<p>Your balance is <span id=\"balance\">$" +
                account.getBalance().getAmount() + "</span></p>";

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(html);
    }
}
