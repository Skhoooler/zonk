import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ZonkServer {
    private static ServerSocketChannel serverSocket;

    public ZonkServer(int port) throws IOException{
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        System.out.println("Zonk Server Started");
    }

    public void start() throws IOException {
        Selector selector = Selector.open();

    }
}
