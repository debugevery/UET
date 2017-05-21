/**
 * Created by muffy on 21/05/2017.
 */
import com.savarese.rocksaw.net.RawSocket;
import sun.security.x509.IPAddressName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

public class Client {
    private String hostName;
    private int sourcePort;
    private int destinationPort;
    private String checkSum;
    private int length;
    private boolean isACK;

    public Client(String hostName, int sourcePort) {
        this.hostName = hostName;
        this.sourcePort = sourcePort;
    }

    class Listener extends Thread {
        public boolean isRunning = true;
        BufferedReader in;

        public Listener(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            super.run();

            while(isRunning) {
                try {
                    if(in.ready()) {
                        System.out.println(in.readLine());
                    }
                    sleep(500);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void runClient() {
        RawSocket socket = new RawSocket();
        if (socket.isOpen()) {
            System.out.println("socket already opened");
        } else {
            try {
                socket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("udp"));
                socket.bind(InetAddress.getByName(hostName));
                System.out.println("hello");
            } catch (IOException e) {
                System.out.println("Failed to connect host Server");
            }
        }

    }

    public static void main(String[] args) throws IOException {
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
