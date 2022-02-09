package CRM.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import CRM.model.Clients;
import CRM.model.Commandes;


public class ClientAdapter implements JsonSerializer <Clients> {

	@Override
	public JsonElement serialize(Clients client, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonObject json = new JsonObject();
		json.addProperty("id", client.getId());
		json.addProperty("nom", client.getNom());
		json.addProperty("prenom", client.getPrenom());
		json.addProperty("entreprise", client.getEntreprise());
		json.addProperty("telephone", client.getTelephone());
		json.addProperty("email", client.getEmail());
		json.addProperty("actif", client.isActif());
		json.addProperty("notes", client.getNotes());
		
		JsonArray commandesJson = new JsonArray();
		JsonObject commandeJson;
		for(Commandes commande : client.getCommandes()) {
			commandeJson = new JsonObject();
			commandeJson.addProperty("id", commande.getId());
			commandeJson.addProperty("label", commande.getLabel());
			commandeJson.addProperty("tjmHT", commande.getTjmHT());
			commandeJson.addProperty("dureeJours", commande.getDureeJours());
			commandeJson.addProperty("TVA", commande.getTVA());
			commandeJson.addProperty("statut", commande.getStatut().toString());
			commandeJson.addProperty("typeCommande", commande.getTypeCommande().toString());
			commandeJson.addProperty("notes", commande.getNotes());
			
			commandesJson.add(commandeJson);
		}
		
		json.add("livres", commandesJson);
		return json;
	}

}
