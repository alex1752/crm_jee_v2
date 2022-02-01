package CRM.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class Tools {
	public static JsonObject getJsonData(HttpServletRequest request) throws IOException {
		JsonObject data = null;
		
		StringBuffer buffer = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) !=null) {
			buffer.append(line);
		}
		
		data = JsonParser.parseString(buffer.toString()).getAsJsonObject();
		
		return data;
	}
	
	public static Properties getConfig() throws IOException{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream ficProps = classLoader.getResourceAsStream("/CRM/config/config.properties");
		Properties properties = new Properties();
		properties.load(ficProps);
		
		return properties;
	}
}
