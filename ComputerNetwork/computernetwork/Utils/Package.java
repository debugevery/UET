package Utils;

import java.io.Serializable;

/**
 * Created by cody on 04/06/2017.
 */
public class Package implements Serializable {
    public String data;
    public String clientIP;
    public int clientPort;

    public Package(String data, String clientIP, int clientPort) {
        this.data = data;
        this.clientIP = clientIP;
        this.clientPort = clientPort;
    }
}
