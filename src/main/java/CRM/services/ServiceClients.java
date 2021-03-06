package CRM.services;

import java.util.List;

import com.google.gson.JsonObject;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.Dao.EntrepriseDao;
import CRM.model.Clients;
import CRM.model.Entreprise;




public class ServiceClients {

	private ClientsDao dao;
	private EntrepriseDao entrepriseDao;
	
	
	public ServiceClients() {
		entrepriseDao = new EntrepriseDao();
		dao = new ClientsDao();
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
	public Long ajouter(JsonObject data) throws ServiceException {
		String nom = null, prenom = null, idEntreprise = null, email = null, telephone = null, actif= null, notes = null ;
		Entreprise entreprise = null;
		Long clientId = null;
		
		try {
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 2, 50);	
			email = ServiceTools.getStringParameter(data, "email", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			telephone = ServiceTools.getStringParameter(data, "telephone", 4, 200, "^\\d+$");	
			actif = ServiceTools.getStringParameter(data, "actif", 2, 50);
			notes = ServiceTools.getStringParameter(data, "notes", 2, 200);
			
			
			if(nom == null)
				throw new ServiceException("Le champ nom est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenom est obligatoire.");
			
			
			if(email == null)
				throw new ServiceException("Le champ email est obligatoire.");
			
			if (dao.existEmail(email)) throw new ServiceException("Cet email est d??j?? pris");
			
			
			Clients client = new Clients(nom, prenom, entreprise, email, telephone, Boolean.parseBoolean(actif), notes);
			dao.ajouter(client);
			clientId = client.getId();
			
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du param??tre idEntreprise ou actif n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
		return clientId;
	}
	
//Modifier
	public void modifier(JsonObject data) throws ServiceException {
		String id = null, nom = null, prenom = null, idEntreprise = null, email = null, telephone = null, actif = null, notes = null ;
		Entreprise entreprise = null; 
		
		try {
			id = ServiceTools.getStringParameter(data, "id", 0, 50, "^\\d+$");
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 3, 50);	
			email = ServiceTools.getStringParameter(data, "email", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			telephone = ServiceTools.getStringParameter(data, "telephone", 4, 200, "^\\d+$");	
			actif = ServiceTools.getStringParameter(data, "actif", 2, 50);
			notes = ServiceTools.getStringParameter(data, "notes", 2, 200);
			
			if(id == null)
				throw new ServiceException("Le champ id est obligatoire.");
			
			
			if(nom == null)
				throw new ServiceException("Le champ nom est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenom est obligatoire.");
			
			if(email == null)
				throw new ServiceException("Le champ email est obligatoire.");
			
			if (dao.existEmail(email,Long.parseLong(id))) throw new ServiceException("Cet email est d??j?? pris");
			
			
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
			throw new ServiceException("Le format du param??tre idClient n'est pas bon.");
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
