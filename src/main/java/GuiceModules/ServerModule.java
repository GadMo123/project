package GuiceModules;

import Configuration.ServerJerseyConfiguration;
import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServerJerseyConfiguration.class).asEagerSingleton();
    }
}

