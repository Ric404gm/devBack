package com.asp.eiyu.ldap.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asp.eiyu.ldap.dto.LoginLdapRequest;
import com.asp.eiyu.ldap.dto.LoginLdapResponse;
import com.asp.eiyu.ldap.service.ILdapConsumingService;


@Service
public class ProviderService implements AuthenticationProvider{

    private static final Logger LOGGER = LoggerFactory.getLogger("AUTHTRACE");
    private static final String LDAP_OK_LOGIN_CODE = "0"; 
    private static final String LDAP_ACTIVE_SESSION_LOGIN_CODE = "6"; 



    private final  ILdapConsumingService ldapConsumingService;

    @Autowired
    public ProviderService (ILdapConsumingService ldapConsumingService) {
        this.ldapConsumingService = ldapConsumingService;
    }



    /*
     * Add your service validation  if  is ok  ? return populated UsernamePasswordAuthenticationToken  else can be return null for unauthorized
     * 
     *  Object username = authentication.getPrincipal();
        Object password = authentication.getCredentials();
        
         UsernamePasswordAuthenticationToken user = 
            new  UsernamePasswordAuthenticationToken(username, password,
                 AuthorityUtils.createAuthorityList("ROLE_USER")); 
        //userDetailsService.loadUserByUsername(null)
        //user.setDetails(authentication.getDetails());
        return user;

         **** Only for Tests ***
        LoginLdapResponse ldapResponse =  ldapConsumingService.processLdapValidation(ldapRequest);       
        ldapResponse = new LoginLdapResponse(); 
        ldapResponse.setNumempleado("404");
        ldapResponse.setIdUsuario("5347");
        ldapResponse.setCodeEstatus("0");
        ldapResponse.setMessageEstatus("msgstatus");
        ldapResponse.setMensaje("MensajeExtra");
        ldapResponse.setData("dataextra"); 

     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    
        LOGGER.info(" Step ProviderService Authentication authenticate....");
        
        Object username = authentication.getPrincipal();
        Object password = authentication.getCredentials();

        LoginLdapRequest ldapRequest = new LoginLdapRequest();
        ldapRequest.setUsuario( username.toString());
        ldapRequest.setContrasena(password.toString());
        ldapRequest.setIdUsuario("0");
        ldapRequest.setIdSesion("0");
        ldapRequest.setEstatus("0");
        ldapRequest.setIdAplicativo("0");
        ldapRequest.setIpUsuario("192.168.151.118");

        
        LoginLdapResponse ldapResponse =  ldapConsumingService.processLdapValidation(ldapRequest);       
     
        if( null == ldapResponse){
            throw new UsernameNotFoundException(" Ha ocurrido un error en la autenticacion Ldap");
        }
        
        if(!LDAP_OK_LOGIN_CODE.toString().equals(ldapResponse.getCodeEstatus())  && !LDAP_ACTIVE_SESSION_LOGIN_CODE.toString().equals(ldapResponse.getCodeEstatus()))
        { 
            throw new UsernameNotFoundException(" Ha ocurrido un error en la autenticacion Ldap");
        }
        return  new  UsernamePasswordAuthenticationToken(username, password,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));

    }

     @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    
    
}
