import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;


public class Main {
    public static void main(String[] args) throws Exception {
        ResourceConfig config = new ResourceConfig();
        config.packages("Resources");

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(new ServletHolder(new ServletContainer(config)), "/*");

        server.start();
        server.join();
    }
}