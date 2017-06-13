package com.ai.core.util.utils;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * 加密解密方法
 * Created by shaoxy on 2014/10/29.
 */
public class CipherUtils {

    private static final Logger log = LoggerFactory.getLogger(CipherUtils.class);
    /**
     * 密钥
     */
    private static byte[] key = {0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79};// "thisIsASecretKey";

    private static String ivStr = "1010101010101010";

    /**
     * 密钥算法
     * java6支持56位密钥，bouncycastle支持64位
     * */
    public static final String KEY_ALGORITHM="AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     *
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     * */
    public static final String CIPHER_ALGORITHM="AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密方法AES
     *
     * @param strToEncrypt
     * @return
     */
    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            final SecretKeySpec secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("utf-8")));
            return encryptedString;
        } catch (Exception e) {
            log.error("Error while encrypting", e);
        }
        return null;

    }

    public static void main(String[] args) {
        String s = "asd";
        System.out.println(encrypt(s));
    }

    /**
     * 解密方法AES
     *
     * @param strToDecrypt
     * @return
     */
    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)),"utf-8");
            return decryptedString;
        } catch (Exception e) {
            log.error("Error while decrypting", e);

        }
        return null;
    }
}
