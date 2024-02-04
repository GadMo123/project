package Configuration;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ServerJerseyConfiguration{

    private static final String jerseyServerConfigFile = "/server.config";
    private ServerConfiguration serverConfiguration;


    public ServerJerseyConfiguration(){
        Gson gson = new Gson();
        InputStream inputStream = this.getClass().getResourceAsStream(jerseyServerConfigFile);
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        serverConfiguration = gson.fromJson(reader, ServerConfiguration.class);
    }

    public ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }
}
