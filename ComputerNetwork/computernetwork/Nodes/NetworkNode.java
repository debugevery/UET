package Nodes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cody on 04/06/2017.
 */
public class NetworkNode {
    public static boolean suppressLog = false;

    public interface Callback {
        void receiveNewData(SocketAddress address, byte[] data);
    }

    public String name;
    protected ConcurrentHashMap<SocketAddress, Socket> connections = new ConcurrentHashMap<>();
    protected void log(String message) {
        if(suppressLog) return;
        System.out.println(name + " -> " + message);
    }

    protected void logE(String message) {
        System.out.println(name + " -> " + message);
    }

    public NetworkNode(String name) {
        this.name = name;
    }

    public boolean connect(SocketAddress address, int localPort) {
        Socket outgoing = new Socket();

        if(connections.containsKey(address))
            return true;

        if(localPort != -1)
            try {
                outgoing.bind(new InetSocketAddress("localhost", localPort));
            } catch (IOException e) {
                logE("can't bind going socket to specified port");
                return false;
            }

        try {
            outgoing.connect(address);
            connections.put(address,
                    outgoing);

            return true;
        } catch (IOException e) {

            logE("can't connect to specified address: " + address.toString());
            return false;
        }
    }

    public boolean send(SocketAddress address, byte[] data) {
        if(!connections.containsKey(address)){
            return false;
        }

        try {
            connections.get(address).getOutputStream().write(data.length);
            connections.get(address).getOutputStream().write(data);

            log(" sent data to: " + address.toString());

            return true;
        } catch (IOException e) {
            logE("can't send data to specified address");
            return false;
        }
    }

    public byte[] read(SocketAddress address) {
        System.out.println(address.toString());

        if(!connections.containsKey(address)){
            return null;
        }

        try{
            int dataLength = connections.get(address).getInputStream().read();
            byte[] data = new byte[dataLength];
            connections.get(address).getInputStream().read(data);

            return data;
        } catch (IOException e) {
            logE("io error while readding");
            return null;
        }
    }

    public void close() {
        Enumeration<Socket> it = connections.elements();
        try {
            while(it.hasMoreElements())
                it.nextElement().close();
        } catch (IOException e) {
            logE("io error while closing");
            e.printStackTrace();
        }
    }
}
