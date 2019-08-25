package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.Money;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidationServlet extends HttpServlet {
    private CashSlot slot;

    public ValidationServlet(CashSlot slot) {
        this.slot = slot;
    }

    private static boolean isValidAmount(String amount) {
        return amount.trim().matches("^[0-9]+([.][0-9]+)?");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

//        TODO low, use json library
        String respOk = "{ \"content\": \"\"  }";
        String respFail = "{ \"content\": \"Cannot dispense. Ask for less.\" }";

        String _amount = req.getParameter("amount").trim();
        if (!isValidAmount(_amount)) {
//            ignore invalid
            resp.getWriter().println(respOk);
            return;
        }

        Money amount = Money.convert(_amount);
        if (slot.canDispense(amount)) {
            resp.getWriter().println(respOk);
        } else {
            resp.getWriter().println(respFail);
        }
    }
}
