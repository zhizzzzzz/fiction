package com.zhi.fiction.util;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.zhi.fiction.exception.BizException;

public class CryptUtil {

    /**
     * RSA公钥
     */
    private static final String publicKey  = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIrBEmiynT1CUMDfAM/qstClI2/8dbQndAEmhhd"
                                             + "ph7fmwRNJDn+6LkNAhydcGb8aVT6jpYy8KSWtCe7cqBZeMdkCAwEAAQ==";

    /**
     * RSA私钥
     */
    private static final String privateKey = "MIIBOwIBAAJBAIrBEmiynT1CUMDfAM/qstClI2/8dbQndAEmhhdph7fmwRNJDn+6\r\n"
                                             + "LkNAhydcGb8aVT6jpYy8KSWtCe7cqBZeMdkCAwEAAQJABumhgifL355rKYCyqKkX\r\n"
                                             + "qX9gqZaZQpVomRoTOh1ezpGUOKKe9VNimRPG6zFFj1L1A+hPzamNUln6NZ50dV++\r\n"
                                             + "tQIhAOpmjVcV0zMeRjbRzGbeubIfEfVkFh0Y0SYlKLeoo21/AiEAl4o/FKTqg+3T\r\n"
                                             + "+RG5Wjq9mgnnLMAGfUcrqhybE/XoPKcCIQDZbCKyoqXzBqPueZB14yawWC2aRypT\r\n"
                                             + "9w8ZqOIp6Z0eFQIgboUoDnFcjE2G3E8d5H2UzGROkqeV1AZ1BQMJ4xvkfV8CIQC6\r\n"
                                             + "5dUA0HUPSFtBuYHX3Oiy0p9b72keWOV/cd/uY65PEg==";

    public static void main(String[] args) {
        //公钥加密  
        byte[] encryptedBytes = encryptByPublicKey("hello world".getBytes());
        System.out.println("加密后：" + new String(Base64.encodeBase64(encryptedBytes)));

        //私钥解密  
        byte[] decryptedBytes = decryptByPrivateKey(encryptedBytes);
        System.out.println("解密后：" + new String(decryptedBytes));
    }

    /**
     * 获取公钥
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new BizException("公钥解析异常");
        }
    }

    /**
     * 获取私钥
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new BizException("私钥解析异常");
        }
    }

    /**
     * RSA公钥加密<base64>
     */
    public static byte[] encryptByPublicKey(byte[] encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            throw new BizException("ras加密异常");
        }
    }

    /**
     * RSA私钥解密<base64>
     */
    public static byte[] decryptByPrivateKey(byte[] decryptedData) {
        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            return cipher.doFinal(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("RSA解密异常");
        }
    }

    /**
     * AES解密
     * decryptedData：待解密数据
     * key：aes的key
     */
    public static String decryptByAES(String message, byte[] key) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec sIvSpec = new IvParameterSpec(key);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, sIvSpec);
            byte[] textBytes = Base64.decodeBase64(message);
            return new String(cipher.doFinal(textBytes), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("AES解密异常");
        }
    }
}
