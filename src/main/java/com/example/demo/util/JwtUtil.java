package com.example.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	 private String secret = "gaurav";

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    
	    
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	
	public String generateToken(String username)
	{
		Map<String,Object> claims = new HashMap<>();
		return createToken(claims,username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 *10))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	public Boolean validateToken(String token,UserDetails userDetails)
	{
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
}
