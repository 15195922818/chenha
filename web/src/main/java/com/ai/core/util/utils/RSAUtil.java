//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ai.core.util.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final int KEY_SIZE = 2048;
    
    private RSAPrivateKey privateKey;
    
    private RSAPublicKey publicKey;

    public RSAUtil() {
    }
    
    public void genKeyPair(){  
        KeyPairGenerator keyPairGen= null;  
        try {  
            keyPairGen= KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        keyPairGen.initialize(1024, new SecureRandom());  
        KeyPair keyPair= keyPairGen.generateKeyPair();  
        this.privateKey= (RSAPrivateKey) keyPair.getPrivate();  
        this.publicKey= (RSAPublicKey) keyPair.getPublic();  
    }

    public static String encryptRSA(String jsonStr, String publicKeyStr) {
        int length = jsonStr.length();
        int size = length / 100 + (length % 100 == 0?0:1);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < size; ++i) {
            int start = i * 100;
            int end = (i + 1) * 100;
            if(end > length) {
                end = length;
            }

            String tmp = jsonStr.substring(start, end);
            byte[] bytes = new byte[1024];

            try {
                bytes = (new BASE64Decoder()).decodeBuffer(publicKeyStr);
            } catch (IOException var14) {
                var14.printStackTrace();
            }

            PublicKey publicKey = restorePublicKey(bytes);
            byte[] encodedText = new byte[1024];

            try {
                encodedText = RSAEncode(publicKey, tmp.getBytes("utf-8"));
            } catch (UnsupportedEncodingException var13) {
                var13.printStackTrace();
            }

            String tmpStr = Base64.encodeBase64String(encodedText);
            sb.append(tmpStr).append("#");
        }

        return sb.toString();
    }

    public static String decryptRSA(String jsonStr, String privateKeyStr) throws UnsupportedEncodingException {
        String[] base64Strs = jsonStr.toString().split("#");
        byte[] bytes = new byte[1024];

        try {
            bytes = (new BASE64Decoder()).decodeBuffer(privateKeyStr);
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        PrivateKey privateKey = restorePrivateKey(bytes);
        StringBuilder sbuilder = new StringBuilder();

        for(int i = 0; i < base64Strs.length; ++i) {
            sbuilder.append(RSADecode(privateKey, Base64.decodeBase64(base64Strs[i])));
        }

        return sbuilder.toString();
    }

    public static Map generateKeyBytes() {
        try {
            KeyPairGenerator e = KeyPairGenerator.getInstance("RSA");
            e.initialize(2048);
            KeyPair keyPair = e.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            HashMap keyMap = new HashMap();
            keyMap.put("publicKey", (new BASE64Encoder()).encode(publicKey.getEncoded()));
            keyMap.put("privateKey", (new BASE64Encoder()).encode(privateKey.getEncoded()));
            System.out.println("PUBLIC_KEY=" + (new BASE64Encoder()).encode(publicKey.getEncoded()));
            System.out.print("PRIVATE_KEY=" + (new BASE64Encoder()).encode(privateKey.getEncoded()));
            return keyMap;
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory e = KeyFactory.getInstance("RSA");
            PublicKey publicKey = e.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

        try {
            KeyFactory e = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = e.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            e.init(1, key);
            return e.doFinal(plainText);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String RSADecode(PrivateKey key, byte[] encodedText) {
        try {
            Cipher e = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            e.init(2, key);
            return new String(e.doFinal(encodedText), "utf-8");
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        generateKeyBytes();
    }
}
