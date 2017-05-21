import java.io.IOException;

/**
 * Created by muffy on 21/05/2017.
 */
public class Server {
    private int portServer;
    private int portmd5;
    private int portsha256;

    public Server(int portServer, int portmd5, int portsha256) {
        this.portServer = portServer;
        this.portmd5 = portmd5;
        this.portsha256 = portsha256;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java Serve <portServer> <portmd5> <portsha256>");
            System.exit(1);
        }

        Server server = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

    }
}
