package com.asp.eiyu.ldap.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
public class LdapUtilCipherTest {
    

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(LdapUtilCipherTest.class);
    private static final String  pwd = "asp2002&";
    

    @Test
    public void getStringAsShaTest()  throws NoSuchAlgorithmException  , UnsupportedEncodingException,
             NoSuchAlgorithmException{
         LOGGER.info(" {}       ",getStringAsSha("admin")); 
        assertNotNull(getStringAsSha("admin"),"passed");
    }

    
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
