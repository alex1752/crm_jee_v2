package CRM.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import CRM.model.Commandes;
import CRM.model.Produit;

public class CommandeAdapter implements JsonSerializer<Commandes>{

	@Override
	public JsonElement serialize(Commandes commande, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("id",commande.getId());
		json.addProperty("label",commande.getLabel());
		json.addProperty("tjmHT", commande.getTjmHT());
		json.addProperty("dureeJours", commande.getDureeJours());
		json.addProperty("TVA", commande.getTVA());
		json.addProperty("statut", commande.getStatut().toString());
		json.addProperty("typeCommande", commande.getTypeCommande().toString());
		json.addProperty("notes", commande.getNotes());
		
		if(!commande.getListProduits().isEmpty()) {
			JsonArray jsonProduits = new JsonArray();
			JsonObject jsonProduit;
			for(Produit p : commande.getListProduits()) {
				jsonProduit = new JsonObject();
				jsonProduit.addProperty("nom", p.getNom());
				jsonProduit.addProperty("prix",p.getPrix());
				jsonProduits.add(jsonProduit);
			}
			json.add("produits", jsonProduits);
		}
			
		if(commande.getClient() != null) {
			JsonObject jsonClient = new JsonObject();
			jsonClient.addProperty("id", commande.getClient().getId());
			jsonClient.addProperty("nom", commande.getClient().getNom());
			jsonClient.addProperty("prenom", commande.getClient().getPrenom());
			if(commande.getClient().getEntreprise() !=null) {
				jsonClient.addProperty("entreprise", commande.getClient().getEntreprise().getNom());
			}
			jsonClient.addProperty("email", commande.getClient().getEmail());
			jsonClient.addProperty("telephone", commande.getClient().getTelephone());
			jsonClient.addProperty("actif", commande.getClient().isActif());
			jsonClient.addProperty("notes", commande.getClient().getNotes());
			
			json.add("client", jsonClient);
		}
		
		return json;
	}

}
