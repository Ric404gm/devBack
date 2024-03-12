package com.asp.eiyu.ldap.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;

public class LdapUtilCipher {
    
    
    private static final Logger LOGGER = 
        org.slf4j.LoggerFactory.getLogger(LdapUtilCipher.class);
    private static final String  pwd = "asp2002&";
    

    
    public  static String  getStringAsSha(String  v) throws   NoSuchAlgorithmException  , UnsupportedEncodingException,
             NoSuchAlgorithmException{
        LOGGER.info(" * Cifrar * {}  ", v);
        return  getSecurePassword(pwd, v) ;
    }


    private static String getSecurePassword(String preHash, String usuario) throws NoSuchAlgorithmException  , UnsupportedEncodingException,
             NoSuchAlgorithmException{
            String generatedPassword = null;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getSalt(preHash, usuario));
            byte[] bytes = md.digest(preHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        
        return generatedPassword;
    }

    private static byte[] getSalt(String preHash, String usuario)   throws UnsupportedEncodingException,
             NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        String secureHash = preHash + usuario; // codigo PRE HASH + USUARIO
        sr.setSeed(secureHash.getBytes("us-ascii"));        
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
