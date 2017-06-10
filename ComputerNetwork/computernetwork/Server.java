import Nodes.NetworkNode;
import Servers.FrontendServer;
import Servers.HashServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cody on 04/06/2017.
 */
public class Server {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
NetworkNode.suppressLog = false;

        InetSocketAddress md5 = new InetSocketAddress("localhost", Integer.parseInt(args[1]));
        InetSocketAddress sha = new InetSocketAddress("localhost", Integer.parseInt(args[2]));
        InetSocketAddress frontend = new InetSocketAddress("localhost", Integer.parseInt(args[0]));

        HashServer md5Server = new HashServer(md5, MessageDigest.getInstance("MD5"));
        HashServer shaServer = new HashServer(sha, MessageDigest.getInstance("SHA-256"));
        FrontendServer frontendServer = new FrontendServer(frontend, md5, sha);
    }
}
