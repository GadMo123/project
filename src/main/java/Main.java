
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        int port = 9000;
        Server server = new Server(port);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});
        server.setHandler(new MyHandler());
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Custom handler to handle the GET request
    static class MyHandler extends AbstractHandler {
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
            // Check if the request path is "/hello" and the method is GET
            if ("/hello".equals(target) && baseRequest.getMethod().equalsIgnoreCase("GET")) {
                // Set the response status and content
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("text/plain");
                response.getWriter().println("Hello, Jetty Server!");
                // Mark the request as handled
                baseRequest.setHandled(true);
            }
        }
    }
}