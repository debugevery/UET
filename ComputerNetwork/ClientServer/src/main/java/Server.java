import java.io.IOException;

/**
 * Created by muffy on 03/06/2017.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java ServerFrontend <portNumber> <portMD5> <portSHA>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        int portMD5 = Integer.parseInt(args[1]);
        int portSHA = Integer.parseInt(args[2]);
        ServerFrontend server = new ServerFrontend(portNumber);
        server.runServer();
        ServerMD5 md5 = new ServerMD5(portMD5);
        md5.runServerMD5();
        server.runServer();

    }
}
