import java.io.*;

public class UserServices {
    private final String serverStoragePath = "src\\Server Storage.txt";

    public UserServices(){

    }

    /**
     * Returns a HostEntry object. Its return code is set to 1 if a matching entry is found. See
     *
     * @return A room code that corresponds to
     */
    private String readFromServerStorage(NetworkInformation networkInformation){


    }

    /**
     * Appends a new line to Server Storage.txt that contains the room code, IP Address and Port Number
     * in this format: [ABCD] [128.128.128.128] [6789].
     */
    protected void writeToServerStorage(String roomCode, NetworkInformation networkInformation) {
        String payload = roomCode + " " + networkInformation.getIp() + " " + networkInformation.getPort() + "\n";
        try{
            File file = new File(serverStoragePath);

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(payload);

            writer.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("File written");
    }
}
