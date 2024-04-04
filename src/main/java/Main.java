import Configuration.ServerJettyConfiguration;
import GuiceModules.ServersModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws Exception {
        readEnvFile(".env");

        //Create Jetty server from config file
        Injector injector = Guice.createInjector(new ServersModule());
        int port = injector.getInstance(ServerJettyConfiguration.class).getPort();
        Server server = new Server(port);

        //Log boot msg
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting at: " + port + " Boot Log Message : " + injector.getInstance(ServerJettyConfiguration.class).getLogMessage());

        //add a servlet with Jersey config to Jetty server
        ServletContextHandler context = new ServletContextHandler(server, "/");
        ResourceConfig config = new ResourceConfig();
        config.packages("Resources");

        context.addServlet(new ServletHolder(new ServletContainer(config)), "/*");

        server.start();
        server.join();
    }


    public static void readEnvFile(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        }

        properties.forEach((key, value) -> System.setProperty("env." + key, (String) value));
    }
}