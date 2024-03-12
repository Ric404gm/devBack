package com.demo.users.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.users.persistence.entity.UserEntity;
import com.demo.users.persistence.repository.UserEntityRepository;

@Service
public class ProviderService  implements AuthenticationProvider {
    
    @Autowired
    private UserEntityRepository entityRepository;

    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        var user  = authentication.getPrincipal(); 
        var credential = authentication.getCredentials();
        var  entity = entityRepository.findByNameAndSecret(user.toString(), credential.toString());

        entity = new UserEntity();

        entity.setSecret("pwd");
        if( ( credential.toString().equals(entity.getSecret()))  ){
            return  new  UsernamePasswordAuthenticationToken(user, credential,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));  
        }
        else
        {
            throw new UsernameNotFoundException(" * Error en logins * ");
        }

    }


  @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    
}
