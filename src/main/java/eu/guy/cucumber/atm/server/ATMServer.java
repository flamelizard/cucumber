package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.CashSlot;
import eu.guy.cucumber.atm.domain.hooks.ServiceHooks;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.javalite.activejdbc.Base;

public class ATMServer {
    //    recommended practice to embed jetty instead of running standalone
    private Server server;

    public ATMServer(int port, CashSlot cashSlot, Account account) {
        System.out.println("[active " + account);
        server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS
        );
        server.setHandler(context);

//        set routes and its handlers
        context.addServlet(new ServletHolder(new MainServlet()), "/");
        context.addServlet(new ServletHolder(
                new WithdrawalServlet(cashSlot, account)), "/withdraw");
        context.addServlet(new ServletHolder(
                new BalanceServlet(account)), "/balance");
    }

    public static void main(String[] args) {
        Base.open("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/events",
                "root", "password");
        try {
            new ATMServer(ServiceHooks.WEBPORT, new CashSlot(), new Account()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        server.start();
        System.out.println("Listening on " + server.getURI());
    }

    public void stop() throws Exception {
        server.stop();
    }
}
