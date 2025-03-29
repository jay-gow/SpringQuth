package com.jaya.SpringSecEx.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jaya.SpringSecEx.Service.JWTservice;
import com.jaya.SpringSecEx.Service.Userservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JWTservice jwtservice;
    @Autowired
    ApplicationContext context;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// bearer get this token validate it "bearer "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb3d0aGFtIiwiaWF0IjoxNzM4NjcwMTY4LCJleHAiOjE3Mzg2NzAyNzZ9.r3eF7lKuIf-MbLoGRB2lLTSlfeHuCbUWOtL26TEIvhk
	String authheader=	request.getHeader("Authorization");
	String token=null;
	String username=null;
	
	if(authheader!=null && authheader.startsWith("Bearer")) {
		token=authheader.substring(7);//index number 7 b0 e2 a2
		username=jwtservice.extractUserName(token);
	}
	
	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
		UserDetails userDetails=context.getBean(Userservice.class).loadUserByUsername(username);
		if(jwtservice.validateToken(token,userDetails)) {
			UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authtoken);
		} 
	}
	filterChain.doFilter(request, response);
	}

}
