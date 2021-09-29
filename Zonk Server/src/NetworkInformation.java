class NetworkInformation {
    private final String ip;
    private final String port;

    public NetworkInformation(String ip, String port){
        this.ip   = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }
}
