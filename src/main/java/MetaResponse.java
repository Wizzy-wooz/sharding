import java.io.Serializable;

/**
 * Created by serb on 13.01.15.
 */
public class MetaResponse implements Serializable{

    private String ipAddress;
    private int port;

    public MetaResponse(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
