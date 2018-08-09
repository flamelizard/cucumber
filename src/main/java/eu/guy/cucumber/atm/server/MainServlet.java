package eu.guy.cucumber.atm.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Servlet handles HTTP requests and response
public class MainServlet extends HttpServlet {
    private String htmlFile = "D:\\projects\\cucumber\\src\\main\\java\\eu\\guy" +
            "\\cucumber\\atm\\server\\index.html";

    protected void doGet(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(ATMServer.slurpFile(htmlFile));
    }
}
