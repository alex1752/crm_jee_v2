package CRM.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import CRM.model.Utilisateurs;

public class UtilisateurAdapter implements JsonSerializer<Utilisateurs> {

	@Override
	public JsonElement serialize(Utilisateurs utilisateur, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("id", utilisateur.getId());
		json.addProperty("login", utilisateur.getLogin());
		json.addProperty("email", utilisateur.getEmail());
		return json;
	}

}
