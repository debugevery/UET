import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    private String hostName;
    private int portNumber;
    private String input;


    public Client(String hostName, int portNumber, String input) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.input = input;
    }

    public void runClient() {
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            out.println(input);
            //System.out.println("Output return: " + in.readLine());
        } catch (UnknownHostException e) {
            System.err.println("Dont know " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Cant connect to " + hostName);
            System.exit(1);
        }

    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java Client <hostName> <portNumber> <StringInput>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String input = args[2];
        Client one = new Client(hostName, portNumber, input);
        one.runClient();


    }
}