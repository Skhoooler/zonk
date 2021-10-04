import java.io.IOException;
import java.time.LocalDateTime;

import java.util.Random;

class HostServices extends UserServices{

    public HostServices(){

    }

    /**
     * Calls generateRoomCode() until it finds a room code that is unique to the
     * room codes found in Server Storage
     * @return a unique room code
     */
    public String getRoomCode(NetworkInformation networkInfo) throws IOException {
        boolean roomCodeIsUnique = false;
        String roomCode = null;

        do {
            roomCode = generateRoomCode();

            // The room code is unique if it is not already in server storage
            roomCodeIsUnique = !searchServerStorage(roomCode).entryFound();

            // Check in right at the start
            roomCheckIn(roomCode);
        } while (!roomCodeIsUnique);

        writeToServerStorage(roomCode, networkInfo);
        return roomCode;
    }

    /**
     * Checks a room code into Server Storage
     * @param roomCode The room code to be checked into Server Storage
     */
    public void roomCheckIn(String roomCode){
        checkIn(roomCode);
    }

    /**
     * Generates a somewhat random, 4-character, alphabetic room code. The first
     * two letters are based on the time of creation, while the second two letters
     * are random.
     * @return A full, 4-letter room code
     */
    private String generateRoomCode(){
        int hour = LocalDateTime.now().getHour();
        int min  = LocalDateTime.now().getMinute();
        Random randomizer = new Random();

        String char1 = intToAlph((randomizer.nextInt(15) * hour) % 25);
        String char2 = intToAlph((randomizer.nextInt(60) * min)  % 25);
        String char3 = intToAlph(randomizer.nextInt(1000) % 25);
        String char4 = intToAlph(randomizer.nextInt(1000) % 25);

        return char1 + char2 + char3 + char4;
    }

    /**
     * Returns the corresponding letter for an inputted number
     * @param i the number to be turned into a letter
     * @return the letter that corresponds to the inputted number.
     */
    private String intToAlph(int i){
        return "ABCDEFGHIGKLMNOPQRSTUVWXYZ".substring(i, i+1);
    }
}
