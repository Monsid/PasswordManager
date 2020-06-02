/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordmanager;

/**
 *
 * @author P225365
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 *
 * @author ownership reference to multiple Programmers who led me here
 * https://www.youtube.com/watch?v=TuYx2ms1jgw&t=36s bouncy castle tutorial with
 * RSA encryption was good but lacked cipher static(couldn't read from file even
 * with saved key, due to cipher, even saved cipher and buffers without success)
 * kept trying different things code got so messy - found this on a chance
 * https://stackoverflow.com/questions/10303767/encrypt-and-decrypt-in-java then
 * i added AES from BouncyCastleProvider (third-party) and i also used it to do some additional
 * encoding before writing to file and it was finally working like a charm.
 * @author P225365 - modified above referance to use pure AES instead of what
 * was there that i can hashes to passwords without \n issues
 */
public class HashEncryption {

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
//HAD TO REMOVE BOUNCYCASTLE FOR JAR (REQUIRES CERTIFICATE https://www.oracle.com/technetwork/java/javase/tech/getcodesigningcertificate-361306.html#jcacodesigning)
//, recommend looking at getting certificate(10days) and reimplementing NetBeans Development Project version
    public HashEncryption() throws Exception {
        myEncryptionKey = "TheUltimateSecretNoOneWillKnow!!!";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }

    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.getEncoder().encode(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }


}