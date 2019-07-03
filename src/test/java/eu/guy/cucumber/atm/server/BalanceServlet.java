package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.Account;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

public class BalanceServlet extends HttpServlet {
    private String htmlFmt = "<html>" +
            "<head><title>Account Balance</title></head>" +
            "<body>%s</body>" +
            "</html>";
    private String body;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
//        servlet runs in own thread and needs new connection
        DataStore.createConnection();

//        .getParameter can access query or post parameters
        Integer accNumber = Integer.valueOf(req.getParameter("account"));
        Account account = Account.getAccountOrNull(accNumber);
        if (account != null) {
            body = "<p>Your balance is <span id=\"acc-balance\">$" +
                    account.getBalance().getAmount() + "</span></p>";
        } else {
            body = "<p>Account number <" + accNumber + "> not found</p>";
        }
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(format(htmlFmt, body));
    }
}
