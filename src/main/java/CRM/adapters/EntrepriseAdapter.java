package CRM.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import CRM.model.Commandes;
import CRM.model.Entreprise;

public class EntrepriseAdapter implements JsonSerializer<Entreprise> {

	@Override
	public JsonElement serialize(Entreprise entreprise, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("id", entreprise.getId());
		json.addProperty("nom", entreprise.getNom());
		json.addProperty("telephone", entreprise.getTelephone());
		json.addProperty("email", entreprise.getEmail());
		json.addProperty("domaine", entreprise.getDomaine());
		json.addProperty("type", entreprise.getType());
		
		if(entreprise.getClient() != null) {
			JsonObject jsonClient = new JsonObject();
			jsonClient.addProperty("id", entreprise.getClient().getId());
			jsonClient.addProperty("nom", entreprise.getClient().getNom());
			jsonClient.addProperty("prenom", entreprise.getClient().getPrenom());
			jsonClient.addProperty("email", entreprise.getClient().getEmail());
			jsonClient.addProperty("telephone", entreprise.getClient().getTelephone());
			jsonClient.addProperty("actif", entreprise.getClient().isActif());
			jsonClient.addProperty("notes", entreprise.getClient().getNotes());

			json.add("client", jsonClient);
		}
		
		return json;
	}

}
