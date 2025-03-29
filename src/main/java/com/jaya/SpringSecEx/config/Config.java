package com.jaya.SpringSecEx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class Config 
{  
	@Autowired
	private UserDetailsService userdetails;
	@Autowired
	private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securitymain(HttpSecurity sec) throws Exception 
    {
//    	sec.csrf(customizer->customizer.disable());
//    	sec.authorizeHttpRequests(request->request.anyRequest().authenticated());
//     sec.formLogin(Customizer.withDefaults());
//       sec.httpBasic(Customizer.withDefaults());
//       sec.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//    	return sec.build() ;
    	
    	
//    	//build a pattern
//    	sec
//    	.csrf(customizer->customizer.disable())
//    	.authorizeHttpRequests(request->request.anyRequest().authenticated())
//    .formLogin(Customizer.withDefaults())
//      .httpBasic(Customizer.withDefaults())
//       .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//    	return sec.build() ;
    	//build a pattern
    	return sec
    	.csrf(customizer->customizer.disable())
    	.authorizeHttpRequests(request->request
    			.requestMatchers("register","jwtlogin")
    			.permitAll()
    			.anyRequest().authenticated())
    .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults())
       .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    	.build() ;}
    
    
    
    
    
    //manual password and username
//    	 @Bean
//    	 public UserDetailsService userdetails() {
//    		 UserDetails user=User.withDefaultPasswordEncoder()
//    				 .username("shri")
//    				 .password("su")
//    				 .roles("user")
//    				 .build();
//    		 UserDetails user1=User.withDefaultPasswordEncoder()
//    				 .username("mama")
//    				 .password("san")
//    				 .roles("user")
//    				 .build();
//    		 return new InMemoryUserDetailsManager(user,user1);
//    	 }
    	 @Bean
    	 public AuthenticationProvider getauthentication() {
    		 DaoAuthenticationProvider provider=new DaoAuthenticationProvider();

    		 provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
    		 provider.setUserDetailsService(userdetails);
    		 return provider;
    	 }
    	 
    	 @Bean
    	 public AuthenticationManager authenticationprovider(AuthenticationConfiguration config) throws Exception {
    		return config.getAuthenticationManager();
    		 
    	 }
    	 
    	 
}
//	 provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

