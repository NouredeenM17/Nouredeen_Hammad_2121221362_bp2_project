
package BP2Project.UtilAndMain;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * @author NouredeenHammad
 * @studentID 2121221362
 */

public abstract class Encrypt {
    
    private static MessageDigest digest;
    
    //This encrypts given string with SHA-256 encoding and returns a hashed string
    public static String hash(String original) throws NoSuchAlgorithmException{
        digest = MessageDigest.getInstance("SHA-256");
        byte[] hashArr = digest.digest(original.getBytes(StandardCharsets.UTF_8));
        
        String encrypted = "";
        for (int i = 0; i < hashArr.length; i++) {
            encrypted += hashArr[i];
        }
        
        return encrypted;
    }
}
