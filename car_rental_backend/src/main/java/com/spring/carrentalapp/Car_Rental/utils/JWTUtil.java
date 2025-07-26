package com.spring.carrentalapp.Car_Rental.utils;

import java.util.*;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	 private final String SECRET_KEY = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";
	    private final long EXPIRATION = 1000 * 60 * 60 * 10; //10h

	    public String generateToken(String username) {
	        return Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
	            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	            .compact();
	    }

	    public String extractUsername(String token) {
	        return Jwts.parser()
	            .setSigningKey(SECRET_KEY)
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject();
	    }
	
}
