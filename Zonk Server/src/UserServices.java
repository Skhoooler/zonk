import java.io.*;

public class UserServices {
    private final String serverStoragePath = "\\Server Storage.txt";

    public UserServices(){

    }

    /**
     *
     * @return
     */
    private String readFromServerStorage(){
        return null;
    }

    /**
     *
     */
    protected void writeToServerStorage(String roomCode, NetworkInformation networkInformation) {
        String payload = roomCode + " " + networkInformation.getIp() + " " + networkInformation.getPort();
        try{
            File file = new File(serverStoragePath);

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(payload);

            writer.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("File written");
    }
}
