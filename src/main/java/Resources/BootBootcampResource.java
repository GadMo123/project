package Resources;

import Configuration.ServerJettyConfiguration;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/boot-bootcamp")
public class BootBootcampResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getBootBootcampMessage() {
        return ServerJettyConfiguration.getINSTANCE().getLogMessage() + " Welcome to Boot Bootcamp! ";
    }
}
