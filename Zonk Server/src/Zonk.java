import java.io.IOException;

/*********************************************************************
 * Zonk Messaging App Server
 *
 * The server's main task is to maintain a map of room codes and port
 * numbers/ip addresses, and then distribute those to requesting clients.
 *
 * All communication with the client is handled by the communicator. It
 * routes requests to the correct place to fulfil them. It may be routed
 * to Host or Guest services, depending on the type of client that made
 * the request.
 *
 * Information is stored within Server Storage
 */

public class Zonk {
    static int DEFAULT_PORT = 6789;

    public static void main(String[] args) throws IOException {
        int port;
        if (args.length == 1){
            port = Integer.parseInt(args[1]);
        }
        else{
            port = DEFAULT_PORT;
        }

        ZonkServer server = new ZonkServer(port);
        server.start();
    }
}
