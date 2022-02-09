package CRM.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import CRM.adapters.UtilisateurAdapter;
import CRM.model.Utilisateurs;
import fr.cleverdev.models.Livre;
import fr.cleverdev.services.adapters.LivreAdapter;


public class ServiceTools {

	public static String getStringParameter(JsonObject data, String nameField, int minLength, int maxLength) throws ServiceException {
		String parameter = null;
		
		if(data.get(nameField) != null && !data.get(nameField).isJsonNull()) {
			parameter = data.get(nameField).getAsString().trim();
			if(parameter.length() < minLength ) {
				throw new ServiceException("Le champ "+nameField+" doit contenir au moins "+minLength+" caract�res.");
			}
			if(parameter.length() > maxLength ) {
				throw new ServiceException("Le champ "+nameField+" doit contenir au maximum "+maxLength+" caract�res.");
			}
		}
		
		return parameter;
	}
	
	public static String getStringParameter(JsonObject data, String nameField, int minLength, int maxLength, String regexFormat) throws ServiceException {
		String parameter = getStringParameter(data, nameField, minLength, maxLength);
		
		if(parameter != null) {
			if(!parameter.matches(regexFormat)) {
				throw new ServiceException("Le champ "+nameField+" n'a pas un format valide (Regex : "+regexFormat+").");
			}
		}
		
		return parameter;
	}
	
	public static Gson getSuperJson() {
		GsonBuilder gsonBuilder = new GsonBuilder()
				.registerTypeAdapter(Utilisateurs.class, new UtilisateurAdapter())
				.serializeNulls();
		return gsonBuilder.create();	
	}
	
}
