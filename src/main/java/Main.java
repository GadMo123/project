import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            int port = 9000;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + port);
            server.createContext("/boot-bootcamp", new GetHandler());
            server.setExecutor(null);
            server.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}