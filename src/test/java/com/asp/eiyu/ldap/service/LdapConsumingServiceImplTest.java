package com.asp.eiyu.ldap.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.asp.eiyu.ldap.dto.LoginLdapRequest;
import com.asp.eiyu.ldap.dto.LoginLdapResponse;

@ExtendWith(MockitoExtension.class)
public class LdapConsumingServiceImplTest {
    
   
    @InjectMocks
    LdapConsumingServiceImpl consumingServiceImpl;

    @Mock
    private RestTemplate restTemplate;


    @Test
    @Order(1)
    @DisplayName("* Test processLdapValidation *")
    public void testprocessLdapValidation()
    {
        ReflectionTestUtils.setField( consumingServiceImpl, "ldapendpoint","http://172.17.7.183:8080/AdminSegWS/rest/capacontrol/loginGlobal");

        ResponseEntity<LoginLdapResponse> responseEntity =
             new ResponseEntity<LoginLdapResponse>( new LoginLdapResponse(), HttpStatusCode.valueOf(200));

        when(restTemplate.postForEntity(anyString(), any(LoginLdapRequest.class),
            Mockito.eq(LoginLdapResponse.class) )).thenReturn(responseEntity);

        assertNotNull( consumingServiceImpl.processLdapValidation(new LoginLdapRequest()), " * OK* ");


    }
    
}
