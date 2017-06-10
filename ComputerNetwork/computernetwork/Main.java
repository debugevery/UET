import Nodes.NetworkNode;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        NetworkNode.suppressLog = true;
        Server.main(new String[] {"6966", "6967", "6968"});
        Client.main(new String[] {"192.168.1.7", "6966"});
    }
}
