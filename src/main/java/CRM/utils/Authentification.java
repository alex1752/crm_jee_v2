package CRM.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

public class Authentification {
	
	
	public static String hashPass(String toHash) throws NoSuchAlgorithmException, IOException {

		byte[] bSalt = Base64.getMimeDecoder().decode(Tools.getConfig().getProperty("salt"));
		
		
        MessageDigest msg = MessageDigest.getInstance("SHA-256");
        msg.update(bSalt);
        
        byte[] hash = msg.digest(toHash.getBytes(StandardCharsets.UTF_8));
        
        // convertir bytes en hexadécimal
        StringBuilder s = new StringBuilder();
        for (byte b : hash) {
            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        
        return s.toString();
	}
	
	
	public static boolean isAuthentificated(HttpServletRequest request) {
		boolean isAuthentificated = false;
		if (request.getHeader("Authorization") != null) {
			String token = request.getHeader("Authorization").replaceAll("Bearer ","");
			return TokenJWT.verifyJWT(token);
		}
		return isAuthentificated;
	}
	
}
