import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServices {
    private final String serverStoragePath = "Server Storage.txt";
    private List<String> checkedIn = new ArrayList<String>();

    public UserServices(){

    }


    /**
     * Searches through Server Storage.txt for entries that contain room codes that have checked in with the
     * server, and extracts them as HostEntries. Server Storage.txt is then deleted, and a new one is populated with the
     * extracted entries.
     */
    public void releaseRoomCodes() throws IOException {
        List<HostEntry> activeEntries = new ArrayList<HostEntry>();

        // Extract all Entries in Server Storage that have checked in
        int i = 0;
        while(i < checkedIn.size()){
            // The room code that checked in is the pattern to search for
            Pattern pattern = Pattern.compile(checkedIn.get(i));
            String foundEntry = findLineInServerStorage(pattern);

            String[] elements = foundEntry.split(" ");
            String roomCode   = elements[0];
            NetworkInformation networkInfo = new NetworkInformation(elements[1], elements[2]);

            activeEntries.add(new HostEntry(roomCode, networkInfo, ReturnCodes.ENTRY_FOUND));
            i++;
        }

        // Delete Old Server Storage.txt
        try {
            Files.delete(Paths.get(serverStoragePath));
        } catch(Exception e){
            e.printStackTrace();
        }

        // Create new Server Storage.txt and populate it with checked in room codes
        for (HostEntry entry:activeEntries) {
            writeToServerStorage(entry);
        }
    }

    /**
     * Checks in a room code to the server. They are held in an ArrayList until releaseRoomCodes() is called.
     * @param roomCode A full, 4-letter room code
     */
    protected void checkIn(String roomCode){
        checkedIn.add(roomCode);
    }

    /**
     * Returns a HostEntry object. Its return code is set to 1 if a matching entry is found. See ReturnCodes.java
     * for more return codes.
     *
     * This method takes a NetworkInformation object to search Server Storage
     * @return A Host Entry object that encapsulates the room code, NetworkInformation and a Return Code
     */
    protected HostEntry searchServerStorage(NetworkInformation networkInformation){
        Pattern pattern = Pattern.compile(networkInformation.getIp() + " " + networkInformation.getPort());
        try{
            String line = findLineInServerStorage(pattern);

            if (!line.equals("Null")){
                String[] elements = line.split(" ");

                return new HostEntry(elements[0], networkInformation, ReturnCodes.ENTRY_FOUND);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new HostEntry("NULL", new NetworkInformation("NULL", "NULL"), ReturnCodes.UNSPECIFIED_ERROR);
        }

        return new HostEntry("NULL", new NetworkInformation("NULL", "NULL"), ReturnCodes.ENTRY_NOT_FOUND);
    }

    /**
     * Returns a HostEntry object. Its return code is set to 1 if a matching entry is found. See ReturnCodes.java
     * for more return codes.
     *
     * This method takes a room code to search Server Storage
     * @return A Host Entry object that encapsulates the room code, NetworkInformation and a Return Code
     */

    protected HostEntry searchServerStorage(String roomCode){
        Pattern pattern = Pattern.compile(roomCode);

        try{
            String line = findLineInServerStorage(pattern);
            if (!line.equals("NULL")){
                String[] elements = line.split(" ");

                return new HostEntry(roomCode, new NetworkInformation(elements[1], elements[2]), ReturnCodes.ENTRY_FOUND);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new HostEntry("NULL", new NetworkInformation("NULL", "NULL"), ReturnCodes.UNSPECIFIED_ERROR);
        }

        return new HostEntry("NULL", new NetworkInformation("NULL", "NULL"), ReturnCodes.ENTRY_NOT_FOUND);
    }

    /**
     * Finds a line within Server Storage that matches the pattern
     * @param pattern a Regex that is used to find a line in server storage
     * @return The raw line as found in server storage
     */
    protected String findLineInServerStorage(Pattern pattern){
        File file = new File(serverStoragePath);
        Matcher matcher;

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                final String line = scanner.nextLine();

                matcher = pattern.matcher(line);
                if (matcher.find()){
                    return line;
                }
            }
            scanner.close();

        }
        catch (Exception e){
            e.printStackTrace();
            return "NULL";
        }
        return "NULL";
    }

    /**
     * Appends a new line to Server Storage.txt that contains the room code, IP Address and Port Number
     * in this format: [ABCD] [128.128.128.128] [6789].
     */
    protected void writeToServerStorage(String roomCode, NetworkInformation networkInformation) {
        // [room code] [IP Address] [Port Number]
        String payload = roomCode + " " + networkInformation.getIp() + " " + networkInformation.getPort() + "\n";
        try{
            File file = new File(serverStoragePath);

            // Will do nothing if the file already exists
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(payload);

            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("File written with Room Code " + roomCode);
    }
    /**
     * Appends a new line to Server Storage.txt that contains the room code, IP Address and Port Number
     * in this format: [ABCD] [128.128.128.128] [6789].
     */
    protected void writeToServerStorage(HostEntry entry) {
        // [room code] [IP Address] [Port Number]
        String payload = entry.getRoomCode() + " " + entry.getNetworkInformation().getIp() + " " + entry.getNetworkInformation().getPort()  + "\n";
        try{
            File file = new File(serverStoragePath);

            // Will do nothing if the file already exists
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(payload);

            writer.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("File written with Room Code " + entry.getRoomCode());
    }
}
