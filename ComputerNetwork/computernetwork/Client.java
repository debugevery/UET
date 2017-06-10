import Nodes.NetworkNode;
import Nodes.ServerNode;
import Servers.HashServer;
import Utils.Helper;
import Utils.Package;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by cody on 04/06/2017.
 */
public class Client {
    public static void main(String[] args) throws IOException {
	NetworkNode.suppressLog = false;

        InetSocketAddress frontend = new InetSocketAddress(args[0], Integer.parseInt(args[1]));

        Vector<String> result = new Vector<>();
        ServerNode clientListener = new ServerNode(0, "Client listener");
        clientListener.setIncomingConnectionCallback(new NetworkNode.Callback() {
            @Override
            public void receiveNewData(SocketAddress address, byte[] data) {
                clientListener.addCallback(address, new NetworkNode.Callback() {
                    int count = 0;
                    String hashAlgo = "null";
                    @Override
                    public void receiveNewData(SocketAddress address, byte[] data) {
                        String message = new String(data, Charset.forName(HashServer.STRING_ENCODE));

                        if(message.equals("exit")) {
                            clientListener.close();
                            return;
                        }

                        if(count % 2 == 1){
                            result.add(hashAlgo + DatatypeConverter.printHexBinary(data));
                            if(result.size() == 2) {
                                System.out.println(result.get(0));
                                System.out.println(result.get(1));
                                result.clear();
                            }
                        }
                        else
                            hashAlgo = new String(data, Charset.forName(HashServer.STRING_ENCODE));

                        count++;
                    }
                });
            }
        });

        NetworkNode clientSender = new NetworkNode("client");
        clientSender.connect(frontend, -1);
	System.out.println("*"+InetAddress.getLocalHost().getHostAddress());
        Scanner in = new Scanner(System.in);
        String message = "ok";
        while(!message.equals("exit")) {
            message = in.nextLine();
            Package pkg = new Package(message, InetAddress.getLocalHost().getHostAddress(), clientListener.getPort());
            byte[] data = Helper.toBytes(pkg);

            clientSender.send(frontend, data);
        }
    }
}
