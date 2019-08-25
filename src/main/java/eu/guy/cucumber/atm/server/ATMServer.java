package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.common.Common;
import eu.guy.cucumber.atm.domain.CashSlot;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.InetSocketAddress;
import java.nio.file.Path;

public class ATMServer {
    //    recommended practice to embed jetty instead of running standalone
    private Server server;

    public ATMServer(int port, CashSlot cashSlot) {
        server = new Server(
                InetSocketAddress.createUnresolved("localhost", port));
        ServletContextHandler servletHandler = new ServletContextHandler(
                ServletContextHandler.SESSIONS
        );
//        URL route -> request handler
        servletHandler.addServlet(new ServletHolder(new MainServlet()), "/");
        servletHandler.addServlet(new ServletHolder(
                new WithdrawalServlet(cashSlot)), "/withdraw");
        servletHandler.addServlet(new ServletHolder(
                new BalanceServlet()), "/balance");
        servletHandler.addServlet(new ServletHolder(
                new ValidationServlet(cashSlot)), "/validate");

//        serve static files
//        must add context matching (url)
        ContextHandler ctxHandler = new ContextHandler();
        ctxHandler.setContextPath("/");
        ResourceHandler resHandler = new ResourceHandler();
        Path staticRoot = Common.getProjectRoot().resolve("src/main/resources/static");
        resHandler.setResourceBase(staticRoot.toString());
        ctxHandler.setHandler(resHandler);

//        collection selects handler based on the longest URL match with request
//        DefaultHandler gives 404
        ContextHandlerCollection handlers = new ContextHandlerCollection();
        handlers.setHandlers(new Handler[]{ctxHandler, servletHandler, new DefaultHandler()});

        server.setHandler(handlers);
    }

    public void start() throws Exception {
        server.start();
        System.out.println("Listening on " + server.getURI());
    }

    public void stop() throws Exception {
        server.stop();
    }
}
