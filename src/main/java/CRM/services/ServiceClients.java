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
	public void ajouter(JsonObject data) throws ServiceException {
		String nom = null, prenom = null, idEntreprise = null, email = null, telephone = null, actif= null, notes = null ;
		Entreprise entreprise = null;
		
		try {
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 2, 50);	
			idEntreprise = ServiceTools.getStringParameter(data, "entreprise", 1, 200,"^\\d+$");
			email = ServiceTools.getStringParameter(data, "email", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			telephone = ServiceTools.getStringParameter(data, "telephone", 4, 200, "^\\d+$");	
			actif = ServiceTools.getStringParameter(data, "actif", 2, 50);
			notes = ServiceTools.getStringParameter(data, "notes", 2, 200);
			
			if(nom == null)
				throw new ServiceException("Le champ nomClient est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomClient est obligatoire.");
			
			if(email == null)
				throw new ServiceException("Le champ emailClient est obligatoire.");
			
			if (dao.existEmail(email)) throw new ServiceException("Cet email est déjà pris");
			
			entreprise = entrepriseDao.trouver(Long.parseLong(idEntreprise));
			
			dao.ajouter(new Clients(nom, prenom, entreprise, email, telephone, Boolean.parseBoolean(actif), notes));
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramètre idEntreprise ou actif n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
//Modifier
	public void modifier(JsonObject data) throws ServiceException {
		String id = null, nom = null, prenom = null, idEntreprise = null, email = null, telephone = null, actif = null, notes = null ;
		Entreprise entreprise; 
		
		try {
			id = ServiceTools.getStringParameter(data, "id", 0, 50, "^\\d+$");
			nom = ServiceTools.getStringParameter(data, "nom", 2, 50);	
			prenom = ServiceTools.getStringParameter(data, "prenom", 3, 50);	
			idEntreprise = ServiceTools.getStringParameter(data, "entreprise", 1, 200,"^\\d+$");
			email = ServiceTools.getStringParameter(data, "email", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			telephone = ServiceTools.getStringParameter(data, "telephone", 4, 200, "^\\d+$");	
			actif = ServiceTools.getStringParameter(data, "actif", 2, 50);
			notes = ServiceTools.getStringParameter(data, "notes", 2, 200);
			
			if(id == null)
				throw new ServiceException("Le champ idClient est obligatoire.");
			
			
			if(nom == null)
				throw new ServiceException("Le champ nomClient est obligatoire.");
			
			if(prenom == null)
				throw new ServiceException("Le champ prenomClient est obligatoire.");
			
			if(email == null)
				throw new ServiceException("Le champ emailClient est obligatoire.");
			
			if (dao.existEmail(email,Long.parseLong(id))) throw new ServiceException("Cet email est déjà pris");
			
			
			Clients client = dao.trouver(Long.parseLong(id));
			if(client == null)
				throw new ServiceException("Le client n'existe pas. Id : "+id);
			
			entreprise = entrepriseDao.trouver(Long.parseLong(idEntreprise));

			
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
