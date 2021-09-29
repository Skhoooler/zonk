public class HostEntry {

    private String roomCode;
    private NetworkInformation networkInformation;
    private ReturnCodes returnCode;

    HostEntry(String roomCode, NetworkInformation networkInformation, ReturnCodes returnCode){
        this.roomCode = roomCode;
        this.networkInformation = networkInformation;
        this.returnCode = returnCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    private void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public NetworkInformation getNetworkInformation() {
        return networkInformation;
    }

    private void setNetworkInformation(NetworkInformation networkInformation) {
        this.networkInformation = networkInformation;
    }

    public ReturnCodes getReturnCode() {
        return returnCode;
    }

    private void setReturnCode(ReturnCodes returnCode) {
        this.returnCode = returnCode;
    }
}
