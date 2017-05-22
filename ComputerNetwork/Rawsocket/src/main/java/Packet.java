import java.net.DatagramPacket;

/**
 * Created by muffy on 22/05/2017.
 */
public class Packet {
    private int sourcePort;
    private int destinationPort;
    private String checkSum;
    private int length;
    private boolean isACK;
    private String message;

    public Packet(int sourcePort, int destinationPort, int length, boolean isACK, String message) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.length = length;
        this.isACK = isACK;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
