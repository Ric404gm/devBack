package com.asp.eiyu.ldap.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
@Configuration
public class SecurityConfiguration {
    
    
	@Autowired
    private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private ProviderService providerService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Value("${aps.login.endpoint}")
    private   String AUTENTICATE_ENDPOINT ;   

    

    /**
     * 
     * @param httpSecurity
     * @return 
     * @throws Exception
     *         return httpSecurity.csrf( (c) -> c.disable())
            .authorizeHttpRequests((request) -> request.requestMatchers(AUTENTICATE_ENDPOINT).permitAll()  Endpoint Autirizado para login
            .anyRequest().authenticated() ) Cualquier solicitud debe ser autenticada
            .authenticationProvider(this.authenticationProvider()) Servicio que funge como autenticador que podria cambiar la funcionalidad
            .addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)  Filtro por cada peticion http a cual quier endpoint valida credenciales y ayuda a autenticar
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   
            .exceptionHandling( (ex) -> ex.authenticationEntryPoint(authenticationEntryPoint) )  Es un componente que ayuda a manejar todos los tipos de error que ocurran al hacer una peticion de autenticacion y cuando no exista pa pagina . 
            .httpBasic(Customizer.withDefaults())  Enable default Response if not exists page
            .build();
            CORS https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
     */   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

     
        return httpSecurity.cors( (cors) -> cors.configurationSource(apiConfigurationSource()))
            .csrf( (c) -> c.disable())
            .authorizeHttpRequests((request) -> request.requestMatchers(AUTENTICATE_ENDPOINT).permitAll()
            .anyRequest().authenticated() )
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   
            .authenticationProvider(this.authenticationProvider())
            .addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling( (ex) -> ex.authenticationEntryPoint(authenticationEntryPoint) )
            .build();
        
        
    } 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {   
        return this.providerService;
    }
   
    /**
     *  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
        }
     * @param http
     * @return
     * @throws Exception
     */    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return new CustomManager();
    }

    CorsConfigurationSource apiConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","PUT"));
		org.springframework.web.cors.UrlBasedCorsConfigurationSource source = 
            new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
