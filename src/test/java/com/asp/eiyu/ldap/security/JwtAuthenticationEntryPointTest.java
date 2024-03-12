package com.asp.eiyu.ldap.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationEntryPointTest {
    

    @InjectMocks
    JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Mock
    HandlerExceptionResolver resolver;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletOutputStream myOutputStream;

	


    
    @Test
    @Order(1)
    @DisplayName("* Test JwtAuthEntryPoint Test *")
    public void testcommence() throws Exception
    {

         
        AuthenticationException authenticationException = 
            new AuthMockException("mock");
        when(response.getOutputStream()).thenReturn(myOutputStream); 
        authenticationEntryPoint.commence(request,response,authenticationException );

        verify( response ).getOutputStream();

    }
    private class AuthMockException extends  AuthenticationException{

        public AuthMockException(String error){
            super(error);
        }
        
    } 
}
