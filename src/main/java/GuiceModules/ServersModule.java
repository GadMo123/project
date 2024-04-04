package GuiceModules;

import Configuration.ServerElasticConfiguration;
import Configuration.ServerJettyConfiguration;
import Utils.EnvVarProvider;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;


public class ServersModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServerJettyConfiguration.class).asEagerSingleton();
        bind(ServerElasticConfiguration.class).asEagerSingleton();
        bind(String.class).annotatedWith(Names.named("JETTY_PORT")).toProvider(new EnvVarProvider("JETTY_PORT"));
        bind(String.class).annotatedWith(Names.named("LOG_MESSAGE")).toProvider(new EnvVarProvider("LOG_MESSAGE"));
        bind(String.class).annotatedWith(Names.named("ELASTIC_NAME")).toProvider(new EnvVarProvider("ELASTIC_NAME"));
        bind(String.class).annotatedWith(Names.named("ELASTIC_PASSWORD")).toProvider(new EnvVarProvider("ELASTIC_PASSWORD"));
        bind(String.class).annotatedWith(Names.named("ES_PORT")).toProvider(new EnvVarProvider("ES_PORT"));
    }



}

