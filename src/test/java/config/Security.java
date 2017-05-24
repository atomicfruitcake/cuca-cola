package config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public abstract class Security {

    private static final Logger LOGGER = Logger.getLogger(Security.class.getName());

    private static SecretKeySpec secretKey;

    public static void setKey(String myKey) {
        MessageDigest sha;
        try {
            byte[] key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Unknown Algorithm for key setting: " + e.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("Unsupported Encoding for key setting: " + e.toString());
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String salt) {
        try {
            setKey(salt);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            LOGGER.severe("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String salt) {
        try {
            setKey(salt);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            LOGGER.severe("Error while decrypting: " + e.toString());
        }
        return null;
    }
}

