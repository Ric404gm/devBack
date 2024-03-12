package com.asp.eiyu.ldap.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.asp.eiyu.ldap.dto.LoginLdapRequest;
import com.asp.eiyu.ldap.dto.LoginLdapResponse;
import com.asp.eiyu.ldap.service.ILdapConsumingService;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceTest {

    
    @InjectMocks
    ProviderService providerService;

    @Mock
    ILdapConsumingService ldapConsumingService;

    
    @Test
    @Order(1)
    @DisplayName("* Test PorviderAuth Service Test *")
    public void testauthenticate()
    {
        var  loginLdapOkResponse = new  LoginLdapResponse(); 
        loginLdapOkResponse.setCodeEstatus("0");
        
        when(ldapConsumingService.processLdapValidation(any( LoginLdapRequest.class) ))
            .thenReturn( loginLdapOkResponse) ;

        assertNotNull(providerService.authenticate(new UsernamePasswordAuthenticationToken("usr", "cred")),
             " * OK *");
    }
}
