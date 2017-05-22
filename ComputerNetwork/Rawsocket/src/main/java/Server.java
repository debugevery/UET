import com.savarese.rocksaw.net.RawSocket;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by muffy on 21/05/2017.
 */
public class Server {
    private int portNumber;
    private String localHost = "127.0.0.1";

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    public void runServer() {
        try {
            MySocket server = new MySocket(portNumber);
            server.open(RawSocket.PF_INET, RawSocket.getProtocolByName("ip"));
            server.bind(InetAddress.getLocalHost());

            while (true) {
                Packet data = server.receive(InetAddress.getByName(localHost));
                if (data != null) {
                    System.out.println(data.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        if (args.length != 3) {
//            System.err.println("Usage: java Serve <portServer> <portmd5> <portsha256>");
//            System.exit(1);
//        }
//
//        Server server = new Server(Integer.parseInt(args[0]));
        Server server = new Server(32000);
        server.runServer();
    }
}
