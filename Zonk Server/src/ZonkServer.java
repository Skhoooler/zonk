import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ZonkServer {
    private static ServerSocketChannel serverSocket;


    public ZonkServer(int port) throws IOException{
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        System.out.println("Zonk Server Started");
    }

    public void start() throws IOException {
        File file = new File("Server Storage.txt");

        // Will do nothing if the file already exists
        file.createNewFile();

        HostServices hostServices = new HostServices();
        hostServices.getRoomCode(new NetworkInformation("42069", "helloThere"));

        /*
        String[] checkingIn = {""};
        for (String roomCode : checkingIn){
            hostServices.roomCheckIn(roomCode);
        }*/

        hostServices.releaseRoomCodes();
    }
}
