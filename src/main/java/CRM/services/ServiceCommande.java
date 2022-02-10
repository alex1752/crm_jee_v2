package CRM.services;

import java.util.List;
import com.google.gson.JsonObject;
import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;
import CRM.model.Commandes;
import CRM.model.Statut;
import CRM.model.TypeCommande;

public class ServiceCommande {

	private CommandesDao dao;
	private ClientsDao daoClient;
	
	public ServiceCommande() {
		dao=new CommandesDao();
		daoClient = new ClientsDao();
	}
	
	public String trouver(long id) throws ServiceException {
		Commandes commande = dao.trouver(id);
		
		if(commande == null)
			throw new ServiceException("La commande n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(commande);
	}
	
	public String lister() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.lister());	
	}
	
	public void ajouter(JsonObject data) throws ServiceException {
		String label = null, idClient = null, statut = null, typeCommande = null, notes = null;
		float dureeJours = -1, tjmHT = -1, TVA = -1 ;
		Statut statutEnum = null;
		TypeCommande typeCommandeEnum = null;
		Clients client = null;
		
		try {
			label = ServiceTools.getStringParameter(data, "label", 2, 255);	
			idClient = ServiceTools.getStringParameter(data, "idClient", 0, 50, "^\\d+$");
			dureeJours = ServiceTools.getFloatParameter(data, "dureeJours", 1, 255);	
			tjmHT = ServiceTools.getFloatParameter(data, "tjmHT", 1, 255);
			TVA = ServiceTools.getFloatParameter(data, "TVA", 1, 255);
			statut = ServiceTools.getStringParameter(data, "statut", 2, 255);	
			typeCommande = ServiceTools.getStringParameter(data, "typeCommande", 2, 255);	
			notes = ServiceTools.getStringParameter(data, "notes", 2, 255);	
			
			if(label == null)
				throw new ServiceException("Le champ label est obligatoire.");
			
			if(typeCommande != null) {
				typeCommandeEnum = TypeCommande.valueOf(typeCommande);
			}else {
				throw new ServiceException("Le champ typeCommande est obligatoire.");
			}
			
			if(statut != null) {
				statutEnum = (Statut.valueOf(statut));
			}else{
				throw new ServiceException("Le champ statut est obligatoire.");
			}
			
			if(idClient != null) {
				client = daoClient.trouver(Long.parseLong(idClient));
				if(client == null)
					throw new ServiceException("Le client n'existe pas. Id : "+idClient);
			}else {
				throw new ServiceException("Le champ idClient est obligatoire");
			}
			
			System.out.println(idClient);
			Commandes commande = new Commandes(label, tjmHT, dureeJours, TVA, statutEnum, typeCommandeEnum, notes, client);
			
			dao.ajouter(commande);

		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void modifier(JsonObject data) throws ServiceException {
		String label = null, idClient = null, statut = null, typeCommande = null, notes = null, id = null;
		float dureeJours = -1, tjmHT = -1, TVA = -1 ;
		Statut statutEnum = null;
		TypeCommande typeCommandeEnum = null;
		Clients client = null;
		
		try {
			id = ServiceTools.getStringParameter(data, "idCommande", 0, 50, "^\\d+$");
			label = ServiceTools.getStringParameter(data, "label", 2, 255);	
			idClient = ServiceTools.getStringParameter(data, "idClient", 0, 50, "^\\d+$");
			dureeJours = ServiceTools.getFloatParameter(data, "dureeJours", 1, 255);	
			tjmHT = ServiceTools.getFloatParameter(data, "tjmHT", 1, 255);
			TVA = ServiceTools.getFloatParameter(data, "TVA", 1, 255);
			statut = ServiceTools.getStringParameter(data, "statut", 2, 255);	
			typeCommande = ServiceTools.getStringParameter(data, "typeCommande", 2, 255);	
			notes = ServiceTools.getStringParameter(data, "notes", 2, 255);	
			
			if(id == null)
				throw new ServiceException("Le champ id est obligatoire.");
			
			if(label == null)
				throw new ServiceException("Le champ label est obligatoire.");
			
			if(typeCommande != null) {
				typeCommandeEnum = TypeCommande.valueOf(typeCommande);
			}else {
				throw new ServiceException("Le champ typeCommande est obligatoire.");
			}
			
			if(statut != null) {
				statutEnum = (Statut.valueOf(statut));
			}else{
				throw new ServiceException("Le champ statut est obligatoire.");
			}
			
			if(idClient != null) {
				client = daoClient.trouver(Long.parseLong(idClient));
				if(client == null)
					throw new ServiceException("Le client n'existe pas. Id : "+idClient);
			}
			

			Commandes commande = dao.trouver(Long.parseLong(id));
			if(commande == null)
				throw new ServiceException("La commande n'existe pas. Id : "+id);
			
			commande.setLabel(label);
			commande.setClient(client);
			commande.setDureeJours(dureeJours);
			commande.setNotes(notes);
			commande.setTypeCommande(typeCommandeEnum);
			commande.setTVA(TVA);
			commande.setTjmHT(tjmHT);
			commande.setStatut(statutEnum);
			
			dao.modifier(commande);
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramètre id n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void supprimer(long id) throws ServiceException {
		try {
			dao.supprimer(id);
		} catch (DaoException e) {
			throw new ServiceException("La commande n'existe pas. Id : "+id);
		}
	}
	
	public String trouverCommandesClient(long idClient) throws ServiceException {
		
		Clients client = daoClient.trouver(idClient);
		if(client == null)
			throw new ServiceException("Le client n'existe pas. Id : "+idClient);
		
		List<Commandes> commandes = dao.trouverCommandesClient(idClient);
		
		if(commandes.isEmpty())
			throw new ServiceException("Le client d' Id : "+idClient+ " n'a pas de commande.");
		
		return ServiceTools.getSuperJson().toJson(commandes);
	}
	
	public String listerParLabel(JsonObject data) throws ServiceException {
		String label = null;
		label = ServiceTools.getStringParameter(data, "label", 2, 255);

		if(label == null)
			throw new ServiceException("Le champ label est obligatoire.");
		
		List<Commandes> commandes = dao.listerParLabel(label);
		
		if(commandes.isEmpty())
			throw new ServiceException("Aucune commande ne correspond au label : " + label);
		return ServiceTools.getSuperJson().toJson(commandes);	
	}
	
}
