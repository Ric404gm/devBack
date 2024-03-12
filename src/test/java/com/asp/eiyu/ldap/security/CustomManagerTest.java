package com.asp.eiyu.ldap.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
public class CustomManagerTest {

    @InjectMocks
    CustomManager customManager;


    @Test
    @Order(1)
    @DisplayName("* Test ManagerTest *")
    public void testauthenticate()
    {   
        assertNotNull(customManager.authenticate(new UsernamePasswordAuthenticationToken("usr", "cred"))," * OK *");
    }
}   
