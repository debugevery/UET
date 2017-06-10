package Servers;

import Utils.Helper;
import Nodes.NetworkNode;
import Nodes.ServerNode;
import Utils.Package;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Created by cody on 04/06/2017.
 */
public class HashServer {
    public static String STRING_ENCODE = "UTF-8";
    
    public HashServer(InetSocketAddress address, MessageDigest messageDigest) throws IOException {
        ServerNode hashServer = new ServerNode(address.getPort(), "Hash Server " + messageDigest.getAlgorithm());

        hashServer.setIncomingConnectionCallback(new NetworkNode.Callback() {
            @Override
            public void receiveNewData(SocketAddress address, byte[] data) {
                hashServer.addCallback(address, new NetworkNode.Callback() {
                    @Override
                    public void receiveNewData(SocketAddress address, byte[] data) {
                        Package pkg = (Package) Helper.toObject(data);

                        InetSocketAddress clientAddress =
                                new InetSocketAddress(pkg.clientIP, pkg.clientPort);
                        hashServer.connect(clientAddress,
                                -1);


                        if(pkg.data.equals("exit")) {
                            hashServer.send(clientAddress, "exit".getBytes(Charset.forName(HashServer.STRING_ENCODE)));
                            hashServer.close();
                            return;
                        }

                        hashServer.send(clientAddress, (messageDigest.getAlgorithm() + " hash: ").getBytes(Charset.forName(HashServer.STRING_ENCODE)));
                        hashServer.send(clientAddress, messageDigest.digest(pkg.data.getBytes(Charset.forName(HashServer.STRING_ENCODE))));
                    }
                });
            }
        });
    }
}
