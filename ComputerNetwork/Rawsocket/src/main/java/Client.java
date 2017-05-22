import com.savarese.rocksaw.net.RawSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Created by muffy on 21/05/2017.
 */

public class Client {
    private String hostName;
    private int sourcePort = 100;
    private int destinationPort;

    public Client(String hostName, int destinationPort) {
        this.hostName = hostName;
        this.destinationPort = destinationPort;
    }

    public void runClient() {
        try {
            MySocket clientSocket = new MySocket(destinationPort);
            clientSocket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
            Scanner scan = new Scanner(System.in);
            String str_input = scan.nextLine();
            Packet packet = new Packet(sourcePort, destinationPort, str_input.length(), false, str_input);
            clientSocket.send(InetAddress.getByName(hostName), packet);
        } catch (java.lang.IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Client <hostName> <portNumber>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        Client one = new Client(hostName, portNumber);
        one.runClient();
    }
}
