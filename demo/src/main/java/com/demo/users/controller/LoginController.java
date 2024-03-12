package com.demo.users.controller;

import org.apache.tomcat.util.http.parser.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.demo.users.configuration.JwtTokenUtil;
import com.demo.users.configuration.ProviderService;
import com.demo.users.dto.LoginRequest;
import com.demo.users.dto.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@CrossOrigin("*")
@RequestMapping("api")
public class LoginController {

    
	private static final Logger LOGGER = LoggerFactory.getLogger("AUTHTRACE");
    
    @Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    
	@Autowired
	private ProviderService providerService;

    @PostMapping(value = "/login",consumes =  org.springframework.http.MediaType.APPLICATION_JSON_VALUE, produces =  org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public  LoginResponse postMethodName(@RequestBody LoginRequest loginRequest) throws Exception{

        Authentication authenticationProvider = providerService.authenticate(  new UsernamePasswordAuthenticationToken( loginRequest.getUser(),
        loginRequest.getPassword()));
			

        Authentication authentication = authenticationManager.
            authenticate(  authenticationProvider);
            
		LOGGER.info(" * Result From autentication : {}",authentication.toString() );
        final String token = jwtTokenUtil.generateToken( loginRequest.getUser());

		return new LoginResponse(token);
    }
    


    @PostMapping("/addUser")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    

}
