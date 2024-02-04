import Configuration.ServerJerseyConfiguration;
import GuiceModules.ServerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("0");
        ResourceConfig config = new ResourceConfig();
        config.packages("Resources");

        System.out.println("1");
        Injector injector = Guice.createInjector(new ServerModule());
        System.out.println("2");
        Server server = new Server(injector.getInstance(ServerJerseyConfiguration.class).getServerConfiguration().getPort());
        System.out.println("3");
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info(injector.getInstance(ServerJerseyConfiguration.class).getServerConfiguration().getLogMessage());

        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(new ServletHolder(new ServletContainer(config)), "/*");

        server.start();
        server.join();
    }
}