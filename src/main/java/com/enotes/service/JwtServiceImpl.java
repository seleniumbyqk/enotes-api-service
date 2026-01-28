package com.enotes.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
import com.enotes.exception.JwtTokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	
	private String secretKey = "";
	
	public JwtServiceImpl()
	{
		try
		{
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			
			SecretKey sk = keyGen.generateKey();
			
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public String generateToken(User user) {
		// TODO Auto-generated method stub
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());
		
		//Token generate
		String token = Jwts.builder()
		.claims().add(claims)
		.subject(user.getEmail())
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis() + 60*60*10))  //10 seconds
		.and()
		.signWith(getKey())
		.compact();
		
		return token;
	}

	private Key getKey() {
		// TODO Auto-generated method stub
		
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}



	@Override
	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		
		Claims claims = extractAllClaims(token);
		
		//claims.getSubject();
		
		return claims.getSubject();
	}

	//Get Role
	public String role(String token)
	{
		Claims claims = extractAllClaims(token);
		
		String role = (String)claims.get("role");
		
		return role;
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		
		/*
		//Without using try-catch
		Claims claims = Jwts.parser()
				.verifyWith(decryptKey(secretKey))
				.build()
				.parseSignedClaims(token)
				.getPayload();
		
		return claims;
		*/
		
		//Using try-catch
		try
		{
			Claims claims = Jwts.parser()
				.verifyWith(decryptKey(secretKey))
				.build()
				.parseSignedClaims(token)
				.getPayload();
			
			return claims;
			
		}
		catch(ExpiredJwtException e)
		{
			throw new JwtTokenExpiredException("Token is expired");
		}
		catch(JwtException e)
		{
			throw new JwtTokenExpiredException("Invalid Jwt Token");
		}
		catch(Exception e)
		{
			throw e;
		}
		
		
	}



	private SecretKey decryptKey(String secretKey) {
		// TODO Auto-generated method stub
		
		//Convert from encrypt to decrypt
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		
		return Keys.hmacShaKeyFor(keyBytes);
		
	}



	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		// TODO Auto-generated method stub
		
		String username = extractUsername(token);
		
		Boolean isExpired = isTokenExpired(token);
		
		if(username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired)
		{
			return true;
		}
		
		return false;
	}



	private Boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		
		Claims claims = extractAllClaims(token);
		
		Date expiredDate = claims.getExpiration();
		
		//Today - 10th, exp - 11th
		
		return expiredDate.before(new Date());
	}

	
	
	
	
}
