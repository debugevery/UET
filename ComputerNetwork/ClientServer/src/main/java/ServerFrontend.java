import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ServerFrontend {
    private int portNumber;
    private String string;
    private SocketAddress clientName;
    private SocketAddress hostName;

    public ServerFrontend(int portNumber) {
        this.portNumber = portNumber;
    }

    public void runServer() throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine = in.readLine();
            this.string = inputLine;
            this.clientName = clientSocket.getRemoteSocketAddress();

            out.println(inputLine);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}