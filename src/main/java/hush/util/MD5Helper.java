package hush.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;


public final class MD5Helper {

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0) { i += 256; }
                if (i < 16) { buf.append("0"); }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String calculateMD5(String source) {
        return Hashing.md5().hashString(source, Charsets.UTF_8).toString();
    }
}
