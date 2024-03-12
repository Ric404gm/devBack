package com.asp.eiyu.ldap.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.asp.eiyu.ldap.mockserver.MockServerLdap;
import com.asp.eiyu.ldap.utils.AesUtil;
import com.asp.eiyu.ldap.utils.AesUtil.OperationType;


@ExtendWith(MockitoExtension.class)
public class AesUtilTest {

    
    @InjectMocks
    AesUtil aesUtil;  

    public static  final String  ENCRYPTED_TEXT = "sns86I6n9aq4prEQtKG3eQ==";
    public static final String DECRYPTED_TEXT = "AASTORGA";

   

    @Test
    @Order(1)
    @DisplayName("  * Test Util *")
	public void  doOperationTest() throws Exception {
    
        ReflectionTestUtils.setField(aesUtil, "aesKey","ZWl5dWtleWFzcDEwMjAyMw==");
        ReflectionTestUtils.setField(aesUtil, "aesIv","ZWl5dWtleWFzcA==");
        
        var result = aesUtil.doOperation(DECRYPTED_TEXT, OperationType.ENCRYPT);
        var resultDecifrado = aesUtil.doOperation(result, OperationType.DECRYPT);

        assertEquals(DECRYPTED_TEXT, resultDecifrado);

    }
}
