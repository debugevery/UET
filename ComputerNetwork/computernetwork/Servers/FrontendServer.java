package Servers;

import Utils.Helper;
import Nodes.NetworkNode;
import Nodes.ServerNode;
import Utils.Package;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by cody on 03/06/2017.
 */
public class FrontendServer {

    public FrontendServer(InetSocketAddress address,InetSocketAddress MD5Address, InetSocketAddress SHAAddress) throws IOException {
        ServerNode frontend = new ServerNode(address.getPort(), "Frontend");

        frontend.connect(MD5Address, -1);
        frontend.connect(SHAAddress, -1);

        frontend.setIncomingConnectionCallback(new NetworkNode.Callback() {
            @Override
            public void receiveNewData(SocketAddress address, byte[] data) {
                frontend.addCallback(address, new NetworkNode.Callback() {
                    @Override
                    public void receiveNewData(SocketAddress address, byte[] data) {
                        Package pkg = (Package) Helper.toObject(data);

                        if(pkg.data.equals("exit")) {
                            frontend.send(MD5Address, data);
                            frontend.send(SHAAddress, data);
                            frontend.close();
                            return;
                        }

                        new Thread() {
                            @Override
                            public void run() {
                                frontend.send(MD5Address, data);
                            }
                        }.start();

                        frontend.send(SHAAddress, data);
                    }
                });
            }
        });
    }
}
