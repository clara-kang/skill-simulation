package communication;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.server.Server;
;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimulationServer {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 8025, "/websockets", SimulationServerEndpoint.class);
        HttpServer httpServer = HttpServer.createSimpleServer();
        CLStaticHttpHandler httpHandler = new CLStaticHttpHandler(SimulationServer.class.getClassLoader());
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/");

        try {
            httpServer.start();
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
