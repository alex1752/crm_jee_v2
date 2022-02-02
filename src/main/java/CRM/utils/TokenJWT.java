package CRM.utils;

import javax.crypto.spec.SecretKeySpec;


import java.io.IOException;
import java.security.Key;
import io.jsonwebtoken.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;   

public class TokenJWT {
	
	//Sample method to construct a JWT
	public static String generateJWT(String email, long ttlMinutes) throws IOException {
	
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Tools.getConfig().getProperty("api_key")), SignatureAlgorithm.HS256.getJcaName());
		
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    
	    //if it has been specified, let's add the expiration
	    long expMillis = nowMillis + (ttlMinutes * 60000);
	    Date exp = new Date(expMillis);

	    String jwtToken = Jwts.builder()
	            .setSubject(email)
	            .setId(UUID.randomUUID().toString())
	            .setIssuedAt(now)
	            .setExpiration(exp)
	            .signWith(hmacKey)
	            .compact();
	    
	    return jwtToken;
	}
	
	
	public static String verifyJWT(String jwtString) {
		String email = null;;		
		try {
			Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Tools.getConfig().getProperty("api_key")), SignatureAlgorithm.HS256.getJcaName());
		
			Jws<Claims> jwt = Jwts.parserBuilder()
				.setSigningKey(hmacKey)
				.build()
				.parseClaimsJws(jwtString);
			 
			 
				email = jwt.getBody().getSubject();

		} catch (Exception e) {
			email = null;
		}

		return email;
	}
	
	
}
