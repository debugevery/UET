/**
 * Created by muffy on 21/05/2017.
 */
public class sha256Server {
    private String string;

    public sha256Server(String string) {
        this.string = string;
    }

    public String SHA256() {
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

    public static void main(String[] args) {
        sha256Server sever = new sha256Server("43593sf435ggkd");
        System.out.println(sever.SHA256());
    }
}
