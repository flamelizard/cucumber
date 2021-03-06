package eu.guy.cucumber.atm.server;

import eu.guy.cucumber.atm.common.Common;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Servlet handles HTTP requests and sends defined response
public class MainServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String indexHtml = Common.getFileFromResources("\\static\\index.html")
                .getAbsolutePath();

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(Common.slurpFile(indexHtml));
    }
}
