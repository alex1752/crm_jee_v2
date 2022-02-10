package CRM.services;

import com.google.gson.JsonObject;

import CRM.Dao.DaoException;
import CRM.Dao.ProduitDao;
import CRM.model.Produit;


public class ServiceProduit {

	private ProduitDao daoProduit;
	
	public ServiceProduit() {
		daoProduit = new ProduitDao ();
	}
	
	//Trouver
	public String trouver(long id) throws ServiceException {
		Produit produit = daoProduit.trouver(id);
		
		if(produit == null)
			throw new ServiceException("Le produit n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(produit);
	}
	
	//Lister
	public String lister() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(daoProduit.lister());	
	}
	
	//Ajouter
	public Long ajouter(JsonObject data) throws ServiceException {
		String nom = null, description = null, prix = null;
		Long produitId = null;
		
		try {
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			description = ServiceTools.getStringParameter(data, "description", 2, 200);	
			prix = ServiceTools.getStringParameter(data, "prix", 2, 6);	
		
			if(nom == null)
				throw new ServiceException("Le champ nom est obligatoire.");
			
			if(prix == null)
				throw new ServiceException("Le champ prix est obligatoire.");

			Produit produit = new Produit(nom, description, Double.parseDouble(prix));
			daoProduit.ajouter(produit);
			produitId = produit.getId();
			
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
		return produitId;
	}
	
	//Modifier
	public void modifier(JsonObject data) throws ServiceException {
		String id= null, nom = null, description = null, prix = null;
		
		try {
			id = ServiceTools.getStringParameter(data, "id", 2, 50, "^\\d+$");	
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			description = ServiceTools.getStringParameter(data, "description", 2, 200);	
			prix = ServiceTools.getStringParameter(data, "prix", 2, 6);	
		
			if(nom == null)
				throw new ServiceException("Le champ nom est obligatoire.");
			
			if(prix == null)
				throw new ServiceException("Le champ prix est obligatoire.");

			Produit produit = daoProduit.trouver(Long.parseLong(id));
			
			produit.setNom(nom);
			produit.setDescription(description);
			produit.setPrix(Long.parseLong(prix));
			
			daoProduit.modifier(produit);
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	
	//Supprimer
	public void supprimer(long id) throws ServiceException {
		try {
			daoProduit.supprimer(id);
		} catch (DaoException e) {
			throw new ServiceException("Le produit n'existe pas. Id : "+id);
		}
	}
	
}
