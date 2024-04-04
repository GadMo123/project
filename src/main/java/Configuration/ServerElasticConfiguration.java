package Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;



public class ServerElasticConfiguration {

    private static ServerElasticConfiguration INSTANCE;

    private final RestClient restClient;
    private final ElasticsearchClient esClient;

    @Inject
    public ServerElasticConfiguration(@Named("ELASTIC_NAME")Provider<String> elasticNameProvider, @Named("ELASTIC_PASSWORD") Provider<String> elasticPasswordProvider, @Named("ES_PORT") Provider<String> elaticPortProvider){
        INSTANCE = this;

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticNameProvider.get(),elasticPasswordProvider.get()));

        //build Elasticsearch client
        String ServerUrl = "http://localhost:" + elaticPortProvider.get();
        restClient = RestClient.builder(HttpHost.create(ServerUrl))
                 .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        esClient = new ElasticsearchClient(transport);
    }

    public static RestClient getRestClient() {
        return INSTANCE.restClient;
    }

    public static ElasticsearchClient getElasticsearchClient() {
        return INSTANCE.esClient;
    }
}
