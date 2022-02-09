package CRM.services;

import java.util.List;

import com.google.gson.JsonObject;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;




public class ServiceClients {

	private ClientsDao dao;
	
	public ServiceClients() {
		
	}
	
//Trouver
	
	public String trouver(long id) throws ServiceException {
		Clients client = dao.trouver(id);
		
		if(client == null)
			throw new ServiceException("Le client n'existe pas. Id : "+id);
		
		return ServiceTools.getSuperJson().toJson(client);
	}

//Lister par nom
	public String listerParNom(String nom) throws ServiceException {
		List<Clients> clients = dao.listerParNom(nom);
		
		if(clients.size()== 0)
			throw new ServiceException("Le client n'existe pas. nom : "+nom);
		
		return ServiceTools.getSuperJson().toJson(clients);
	}
	
	
//Lister
	
	public String lister() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.lister());	
	}
	
//Ajouter
	public void ajouter(JsonObject data) throws ServiceException {
		String nom = null, prenom = null, entreprise = null, email = null, telephone = null, actif= null, notes = null ;
		
		
		try {
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 2, 50);	
			entreprise = ServiceTools.getStringParameter(data, "entreprise", 2, 200);
			email = ServiceTools.getStringParameter(data, "email", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			telephone = ServiceTools.getStringParameter(data, "telephone", 4, 200, "^\\d+$");	
			actif = ServiceTools.getStringParameter(data, "actif", 2, 50);
			notes = ServiceTools.getStringParameter(data, "note", 2, 200);
			
			if(nom == null)
				throw new ServiceException("Le champ nomClient est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomClient est obligatoire.");
			
			if(email == null)
				throw new ServiceException("Le champ emailClient est obligatoire.");
			
			dao.ajouter(new Clients(nom, prenom, entreprise, email, telephone, Boolean.parseBoolean(actif), notes));
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
//Modifier
	public void modifier(JsonObject data) throws ServiceException {
		String id = null, nom = null, prenom = null, entreprise = null, email = null, telephone = null, actif = null, notes = null ;
		
		
		try {
			id = ServiceTools.getStringParameter(data, "id", 0, 50, "^\\d+$");
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 3, 50);	
			entreprise = ServiceTools.getStringParameter(data, "entreprise", 3, 200);
			email = ServiceTools.getStringParameter(data, "email", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			telephone = ServiceTools.getStringParameter(data, "telephone", 4, 200, "^\\d+$");	
			actif = ServiceTools.getStringParameter(data, "actif", 2, 50);
			notes = ServiceTools.getStringParameter(data, "note", 2, 200);
			
			if(id == null)
				throw new ServiceException("Le champ idClient est obligatoire.");
			
			
			if(nom == null)
				throw new ServiceException("Le champ nomClient est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomClient est obligatoire.");
			
			if(email == null)
				throw new ServiceException("Le champ emailClient est obligatoire.");
			
			Clients client = dao.trouver(Long.parseLong(id));
			if(client == null)
				throw new ServiceException("Le client n'existe pas. Id : "+id);
			
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setEntreprise(entreprise);
			client.setEmail(email);
			client.setTelephone(telephone);
			client.setActif(Boolean.parseBoolean(actif));
			client.setNotes(notes);
		
			dao.modifier(client);
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramètre idClient n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
//Supprimer
	public void supprimer(long id) throws ServiceException {
		try {
			dao.supprimer(id);
		} catch (DaoException e) {
			throw new ServiceException("Le client n'existe pas. Id : "+id);
		}
	}
	
}
