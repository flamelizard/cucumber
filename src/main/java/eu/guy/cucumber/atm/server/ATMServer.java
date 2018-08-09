package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.domain.Account;
import eu.guy.cucumber.atm.domain.CashSlot;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ATMServer {
    //    recommended practice to embed jetty instead of running standalone
    private Server server;

    public ATMServer(int port, CashSlot cashSlot, Account account) {
        server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS
        );
        server.setHandler(context);
        context.addServlet(new ServletHolder(new MainServlet()), "/");
        context.addServlet(new ServletHolder(
                new WithdrawalServlet(cashSlot, account)), "/withdraw");
        context.addServlet(new ServletHolder(
                new BalanceServlet(account)), "/balance");
    }

    public static String slurpFile(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        if (!Files.isRegularFile(path))
            throw new FileNotFoundException();
        return Files.readAllLines(path).stream()
                .collect(Collectors.joining("\n"));
    }

    public void start() throws Exception {
        server.start();
        System.out.println("Listening on " + server.getURI());
    }

    public void stop() throws Exception {
        server.stop();
    }
}
