import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServices {
    private final String serverStoragePath = "src\\Server Storage.txt";

    public UserServices(){

    }

    /**
     * Returns a HostEntry object. Its return code is set to 1 if a matching entry is found. See ReturnCodes.java
     * for more return codes.
     *
     * This method takes a NetworkInformation object to search Server Storage
     * @return A Host Entry object that encapsulates the room code, NetworkInformation and a Return Code
     */

    private HostEntry readFromServerStorage(NetworkInformation networkInformation){
        File file = new File(serverStoragePath);
        try {
            Scanner scanner = new Scanner(file);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a HostEntry object. Its return code is set to 1 if a matching entry is found. See ReturnCodes.java
     * for more return codes.
     *
     * This method takes a room code to search Server Storage
     * @return A Host Entry object that encapsulates the room code, NetworkInformation and a Return Code
     */

    private HostEntry readFromServerStorage(String roomCode){
        File file = new File(serverStoragePath);
        Pattern pattern = Pattern.compile(roomCode);
        Matcher matcher = null;

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                final String line = scanner.nextLine();

                matcher = pattern.matcher(line);
                if (matcher.find()){
                    String[] entry = line.split(" ");

                    String foundRoomCode = entry[0];
                    NetworkInformation foundNetworkInfo = new NetworkInformation(entry[1], entry[2]);

                    return new HostEntry(foundRoomCode, foundNetworkInfo, ReturnCodes.ENTRY_FOUND);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new HostEntry("NULL", new NetworkInformation("NULL", "NULL"), ReturnCodes.UNSPECIFIED_ERROR);
        }

        return new HostEntry("NULL", new NetworkInformation("NULL", "NULL"), ReturnCodes.ENTRY_NOT_FOUND);
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
