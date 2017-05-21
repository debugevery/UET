/**
 * Created by muffy on 21/05/2017.
 */

import sun.security.provider.MD5;

import java.security.*;
public class md5Server {
    private String string;

    public md5Server(String string) {
        this.string = string;
    }

    public String MD5() {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
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
        md5Server sever = new md5Server("43593sf435ggkd");
        System.out.println(sever.MD5());
    }
}
