package CRM.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import CRM.model.Commandes;
import CRM.model.Produit;

public class ProduitAdapter implements JsonSerializer<Produit> {

	@Override
	public JsonElement serialize(Produit produit, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonObject json = new JsonObject();
		json.addProperty("id", produit.getId());
		json.addProperty("nom", produit.getNom());
		json.addProperty("description", produit.getDescription());
		json.addProperty("prix", produit.getPrix());
		
		JsonArray commandesJson = new JsonArray();
		JsonObject commandeJson;
		for(Commandes commande : produit.getListCommandes()) {
			commandeJson = new JsonObject();
			commandeJson.addProperty("id",commande.getId());
			commandeJson.addProperty("label",commande.getLabel());
			commandeJson.addProperty("tjmHT", commande.getTjmHT());
			commandeJson.addProperty("dureeJours", commande.getDureeJours());
			commandeJson.addProperty("TVA", commande.getTVA());
			commandeJson.addProperty("statut", commande.getStatut().toString());
			commandeJson.addProperty("typeCommande", commande.getTypeCommande().toString());
			commandeJson.addProperty("notes", commande.getNotes());
			
			commandesJson.add(commandeJson);
		}
		
		json.add("Commandes", commandesJson);
		return json;
	}

	
	
}
