package Resources;

import Configuration.ServerElasticConfiguration;
import Records.IndexDocument;
import Records.Message;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/index")
public class IndexResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{index_id}")
    public Response indexMessage(@HeaderParam("User-Agent") String userAgent, @PathParam("index_id") String indexId, Message message){
        try {
            //IndexDocument indexDocument = new IndexDocument(getMessage(message), userAgent);
            IndexDocument indexDocument = new IndexDocument(message.message(), userAgent);

            // Access the ElasticsearchClient from Elasticsearch singleton
            ElasticsearchClient esClient = ServerElasticConfiguration.getElasticsearchClient();

            IndexResponse response = esClient.index(i -> i
                    .index("index")
                    .id(indexId)
                    .document(indexDocument)
            );
            return Response.ok("Document indexed successfully " + indexId + " todo "  + response.version()).build();
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(IndexResource.class);
            logger.info("Document indexed failed");
            throw new RuntimeException(e);
        }
    }
    //public static String getMessage(String message) { return message.substring(8); }
}
