package com.jaya.SpringSecEx.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTservice {

    private String secretKey = "";

    public JWTservice() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
           System.out.println(e);
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
    	
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(subject)
				.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
						.and()
						.signWith(getKey())
						.compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
//package com.jaya.SpringSecEx.Service;
//
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class JWTservice {
//	
//	private String secretkey="";
//
//	public JWTservice() {
//		try {
//			KeyGenerator keygen=KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk=keygen.generateKey();
//			secretkey=Base64.getEncoder().encodeToString(sk.getEncoded());
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public String generateToken(String user)
//	{
//		Map<String, Object> claims=new HashMap<String, Object>();
//		return Jwts.builder()
//				.claims()
//				.add(claims)
//				.subject(user)
//				.issuedAt(new Date(System.currentTimeMillis()))
//						.expiration(new Date(System.currentTimeMillis()+60*60*30))
//						.and()
//						.signWith(getkey())
//						.compact();
//
////		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpheWEiLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTUxNjI0OTAyMn0.p21l1QG6_Kx34P4Gu50-ujZrqxf3vODi1BeIbjkAohQ";
//	}
//
//	private Key getkey()
//	{
//		byte[] keybytes=Decoders.BASE64.decode(secretkey);
//		return   Keys.hmacShaKeyFor(keybytes);
//	}
//
//	public String extractUserName(String token) {
//		//extract the username from jwt token
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	private String extractClaim(String token, Function<Claims,T> claimresolver) {
//		
//		final Claims claims=extractAllClaims(token);
//		
//		return claimresolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		// TODO Auto-generated method stub
//		return Jwts.parser()
//				.setSigningKey(getkey())
//				.build().parseClaimsJwt(token).getBody();
//	}
//
//	public boolean validateToken(String token, UserDetails userDetails) {
//		
//		final String userName= extractUserName(token) ;
//		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));}
//		private boolean isTokenExpired(String token) {
//		return extractExpiration(token) . before(new Date());}
//		private Date extractExpiration(String token) {
//		return extractClaim(token,Claims::getExpiration) ;}
//
//}
