package com.demo.users.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.demo.users.persistence.entity.UserEntity;
import com.demo.users.persistence.repository.UserEntityRepository;

public class CustomAuthenticatonManager implements AuthenticationManager {


   
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
             authentication.getCredentials(),AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
