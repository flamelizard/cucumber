package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.CashSlot;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.InetSocketAddress;

public class ATMServer {
    //    recommended practice to embed jetty instead of running standalone
    private Server server;

    public ATMServer(int port, CashSlot cashSlot) {
//        TODO binds to DockerNAT 10.75, grab server endpoint programmatically
        server = new Server(
                InetSocketAddress.createUnresolved("localhost", port));
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS
        );
        server.setHandler(context);

//        set routes and its handlers
        context.addServlet(new ServletHolder(new MainServlet()), "/");
        context.addServlet(new ServletHolder(
                new WithdrawalServlet(cashSlot)), "/withdraw");
        context.addServlet(new ServletHolder(
                new BalanceServlet()), "/balance");
    }

    public static void main(String[] args) throws Exception {
        ATMServer server = new ATMServer(5000, new CashSlot());
        server.start();
    }

    public void start() throws Exception {
        server.start();
        System.out.println("Listening on " + server.getURI());
    }

    public void stop() throws Exception {
        server.stop();
    }
}
