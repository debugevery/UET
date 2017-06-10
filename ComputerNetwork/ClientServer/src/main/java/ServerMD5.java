import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by muffy on 03/06/2017.
 */
public class ServerMD5 {
    private int portMD5;

    public ServerMD5(int portMD5) {
        this.portMD5 = portMD5;
    }

    public void runServerMD5() throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(portMD5);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            if (in.readLine() != null) {
                String inputLine = in.readLine();
                String md5 = MD5(inputLine);
                out.println(md5);
                System.out.println(md5);
            }
            System.out.println("md5");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String MD5(String string) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(string.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
