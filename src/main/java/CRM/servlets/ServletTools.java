package CRM.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ServletTools {
	public static JsonObject getJsonFromBuffer(HttpServletRequest request) throws IOException, JsonSyntaxException {
		StringBuffer buffer = new StringBuffer();
		String line = null;
  		BufferedReader reader = request.getReader();
  		while ((line = reader.readLine()) != null) {
  			buffer.append(line);
  		}

		JsonObject data = JsonParser.parseString(buffer.toString()).getAsJsonObject();
		
		return data;
	}
	
	public static void sendResponse(HttpServletResponse response, int responseStatusCode, String responseContentType, String responseContent) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(responseStatusCode);
		response.setContentType(responseContentType);
		response.getWriter().write(responseContent);
	}
	
}
