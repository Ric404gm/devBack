package com.asp.eiyu.ldap.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesUtil {

    private static final  Logger LOGGER = LoggerFactory.getLogger(AesUtil.class);

    @Value("${asp.login.aes.key}")
    private final String  aesKey;
    
    
    @Value("${asp.login.aes.iv}")
    private final String aesIv;

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    
    public AesUtil ( @Value("${asp.login.aes.key}") String  aesKey, @Value("${asp.login.aes.iv}") String aesIv){
        this.aesKey = aesKey;
        this.aesIv = aesIv;
    }

    public  String  doOperation(String  text, OperationType operationType) throws IllegalArgumentException,NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException
    {
        SecretKey secretKey = decodeBase64ToAESKey(this.aesKey);
		IvParameterSpec ivParameterSpec = new  IvParameterSpec(this.aesIv.getBytes());
        LOGGER.info(" Aesutiloperation: key{} , iv{} , operation{}  , text{} ",this.aesKey,this.aesIv,operationType,text);

        if(operationType.equals(OperationType.ENCRYPT))
        {
            return this.encrypt(this.ALGORITHM, text, secretKey, ivParameterSpec);
        }else if (operationType.equals(OperationType.DECRYPT)){
            return this.decrypt(this.ALGORITHM, text, secretKey, ivParameterSpec);
        } 
        else{
            throw new IllegalArgumentException(" Opcion no valida");
        }
        
    }

    private static  String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException 
	{
    
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(plainText);
	}

	private static String encrypt(String algorithm, String input, SecretKey key,IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException 
	{
    
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(cipherText);
	}

   	private static SecretKey decodeBase64ToAESKey(final String encodedKey) throws IllegalArgumentException 
	{
		try {
			
            final byte[] keyData = Base64.getDecoder().decode(encodedKey);
			final int keysize = keyData.length * Byte.SIZE;
			switch (keysize) {
			case 128:
			case 192:
			case 256:
				break;
			default:
				throw new IllegalArgumentException("Invalid key size for AES: " + keysize);
			}

			if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
				throw new IllegalArgumentException("Key size of " + keysize + " not supported in this runtime");
			}

			final SecretKeySpec aesKey = new SecretKeySpec(keyData, "AES");
			return aesKey;
		} catch (final NoSuchAlgorithmException e) {
			
            throw new IllegalStateException("AES should always be present in a Java SE runtime", e);
		}
	} 

    public  enum OperationType {

        DECRYPT,
        ENCRYPT, 
        
    }


}
