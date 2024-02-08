package de.bwvaachen.botscheduler.grassmann.modal;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class StringEncryption {
    public static String encrypt(String strToEncrypt, String secret) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String strToDecrypt, String secret) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
        return new String(decryptedBytes);
    }
}