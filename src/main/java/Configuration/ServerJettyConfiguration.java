package Configuration;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class ServerJettyConfiguration {

    private int jettyPort;
    private String logMessage;

    public static ServerJettyConfiguration getINSTANCE() {
        return INSTANCE;
    }

    private static ServerJettyConfiguration INSTANCE;

    @Inject
    public ServerJettyConfiguration( @Named("JETTY_PORT") Provider<String> jettyPortProvider,  @Named("LOG_MESSAGE") Provider<String> logMessageProvider){
        jettyPort = Integer.parseInt(jettyPortProvider.get());
        logMessage = logMessageProvider.get();
        INSTANCE = this;
    }

    public int getPort() {return jettyPort;}

    public String getLogMessage() {
        return logMessage;
    }
}
