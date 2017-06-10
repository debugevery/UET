/**
 * Created by muffy on 03/06/2017.
 */
public class ServerSHA256 {
    private int portSHA;

    public ServerSHA256(int portSHA) {
        this.portSHA = portSHA;
    }

    public String SHA256(String string) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] array = md.digest(string.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
