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
    private static final String publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsUDnLUAvktiSs9EPMw8ZMiS1EoWBbYUXukyR+sZx3pirvleWa3zW1N1KacULEnJZaMg2OjDVihvhb8OFUww2mbBtYgLLOOAJl02T8PWIiPzp8iBKaifzOD3SDNn5jcaDDxL/EANB4C2eRfreyQaSFG+1iwUYH84CRPSJh+QbcLwIDAQAB";

    /**
     * RSA私钥
     */
    private static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKxQOctQC+S2JKz0Q8zDxkyJLUShYFthRe6TJH6xnHemKu+V5ZrfNbU3UppxQsSclloyDY6MNWKG+Fvw4VTDDaZsG1iAss44AmXTZPw9YiI/OnyIEpqJ/M4PdIM2fmNxoMPEv8QA0HgLZ5F+t7JBpIUb7WLBRgfzgJE9ImH5BtwvAgMBAAECgYAr4dp2DpKY0lIQwbwqf4kWoLCok7hoTSN0GIDoyu6gAe9ZSIFKqaGR3t7oegOY8o/PtIODU+2cg+bn8zZPUcU9uqxnNfo5nVA5WlOhV4ESpyXduPR4+XL5eYDrMdQxTO+r7Vgc4BKtrN3Q+AuxYmO4HjG8xJnvXdix/hzzAY/sQQJBAPHL4L+XNk8Pp+S+mgXmyfUJKL5c4jyvRi++V0rydw2LsQarcCghX/bYbzjJ2yl3xczjij68flQxMifNV5O4rXECQQC2b3c6G1LVEmvNIrExVw0wYqSEcCU8xMn5wBmn4eTTu1w6qIZmPBbzxez1ZEMrHcTkru9UFNey4YzPd1IEz9OfAkEAr6IcGo7H0B3bIniggHDneamxgrUNUG8wWDW2SqZce+ZgLCwTR9MWVcNY5rMZEsIBV5ZgMLnSOW2StP6fApo+wQJAShgBS1c3kBQYNktFve1GDQWzHZrSdNjT0xxjDx0eq6OntKcnVrRG+iDDuXJMGWHQyrQFvZdtd1hFsSkb5ZpmrwJBAMaA4OLmh20gKpBY3tB4R1PkxwKjEKgdi8CM5xRKNCXm41yigi9kOCXDm/Rg87XM5l4aiSWaIeQTO+llqH/eQCM=";

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
