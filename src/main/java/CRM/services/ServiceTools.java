package CRM.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import CRM.services.ServiceException;
import CRM.adapters.ClientAdapter;
import CRM.adapters.CommandeAdapter;
import CRM.adapters.ModificationAdapter;
import CRM.adapters.UtilisateurAdapter;
import CRM.model.Clients;
import CRM.model.Commandes;
import CRM.model.Modification;
import CRM.model.Utilisateurs;


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
	
	public static float getFloatParameter(JsonObject data, String nameField, int minValue, int maxValue) throws ServiceException {
		float parameter = 0;
		
		if(data.get(nameField) != null && !data.get(nameField).isJsonNull()) {
			parameter = data.get(nameField).getAsFloat();
			if(parameter < minValue ) {
				throw new ServiceException("Le champ "+nameField+" doit être avoir une valeur supérieur à"+minValue);
			}
			if(parameter > maxValue ) {
				throw new ServiceException("Le champ "+nameField+" doit être avoir une valeur supérieur à"+maxValue);
			}
		}
		
		return parameter;
	}
	
	public static Gson getSuperJson() {
		GsonBuilder gsonBuilder = new GsonBuilder()
				.registerTypeAdapter(Utilisateurs.class, new UtilisateurAdapter())
				.registerTypeAdapter(Clients.class, new ClientAdapter())
				.registerTypeAdapter(Commandes.class, new CommandeAdapter())
				.registerTypeAdapter(Modification.class, new ModificationAdapter())
				.serializeNulls();
		return gsonBuilder.create();	
	}
	
}
