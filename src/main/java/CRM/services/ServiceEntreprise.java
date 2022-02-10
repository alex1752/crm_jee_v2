package CRM.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.Dao.EntrepriseDao;
import CRM.model.Clients;
import CRM.model.Entreprise;
import CRM.model.Utilisateurs;
import CRM.utils.Authentification;
import CRM.utils.TokenJWT;

public class ServiceEntreprise {

	private EntrepriseDao entrepriseDao;
	private ClientsDao clientDao;

	public ServiceEntreprise() {
		this.entrepriseDao = new EntrepriseDao();
		this.clientDao = new ClientsDao();
	}
	
	
	public String lister() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(entrepriseDao.lister());

	}
	
	public String trouver(long id) throws ServiceException {
		Entreprise entreprise = entrepriseDao.trouver(id); 
		if (entreprise == null) throw new ServiceException("L'entreprise n'existe pas. Id : "+id);
		return ServiceTools.getSuperJson().toJson(entreprise);
	}

	public void supprimer(long id) throws ServiceException {
		try {
			entrepriseDao.supprimer(id);
		} catch (DaoException e) {
			throw new ServiceException("L'entreprise n'existe pas. Id : "+id);
		}
	}
	
	public void ajouter(JsonObject data) throws ServiceException, NoSuchAlgorithmException, IOException {
		String nom = null, telephone = null, email = null, domaine = null, type = null, idClient = null;
		Clients client = null;
		Entreprise entreprise = null;
		
		
		try {
			
			nom = ServiceTools.getStringParameter(data, "nomEntreprise", 1, 200);	
			telephone = ServiceTools.getStringParameter(data, "telephoneEntreprise", 4, 200, "^\\d+$");	
			email = ServiceTools.getStringParameter(data, "emailEntreprise", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			domaine = ServiceTools.getStringParameter(data, "domaineEntreprise", 1, 200);	
			type = ServiceTools.getStringParameter(data, "typeEntreprise", 1, 200);	
			idClient = ServiceTools.getStringParameter(data, "idClient", 1, 200, "^\\d+$");	

			if (idClient == null) throw new ServiceException("Le champ idClient est obligatoire.");
			if (nom == null) throw new ServiceException("Le champ nomEntreprise est obligatoire");
			if (email == null) throw new ServiceException("Le champ emailEntreprise est obligatoire");
			
			// if (entrepriseDao.existEmail(email)) throw new ServiceException("Cet email est déjà pris");
			
			if(idClient != null) {
				client = clientDao.trouver(Long.parseLong(idClient));
				if(client == null) throw new ServiceException("Le client n'existe pas. Id : "+idClient);
			}
			entreprise = new Entreprise(nom,telephone,email,domaine,type);
			entreprise.setClient(client);
			
			entrepriseDao.ajouter(entreprise);
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramètre idClient n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	
	public void modifier(JsonObject data) throws ServiceException, NoSuchAlgorithmException, IOException {
		String id = null, nom = null, telephone = null, email = null, domaine = null, type = null, idClient = null;
		Clients client = null;
		Entreprise entreprise = null;
		
		
		try {
			id = ServiceTools.getStringParameter(data, "idEntreprise", 1, 200, "^\\d+$");	
			nom = ServiceTools.getStringParameter(data, "nomEntreprise", 1, 200);	
			telephone = ServiceTools.getStringParameter(data, "telephoneEntreprise", 4, 200, "^\\d+$");	
			email = ServiceTools.getStringParameter(data, "emailEntreprise", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			domaine = ServiceTools.getStringParameter(data, "domaineEntreprise", 1, 200);	
			type = ServiceTools.getStringParameter(data, "typeEntreprise", 1, 200);	
			idClient = ServiceTools.getStringParameter(data, "idClient", 1, 200, "^\\d+$");	

			if (id == null) throw new ServiceException("Le champ idEntreprise est obligatoire.");
			if (idClient == null) throw new ServiceException("Le champ idClient est obligatoire.");
			if (nom == null) throw new ServiceException("Le champ nomEntreprise est obligatoire");
			if (email == null) throw new ServiceException("Le champ emailEntreprise est obligatoire");
			
			// if (entrepriseDao.existEmail(email)) throw new ServiceException("Cet email est déjà pris");
			
			if(idClient != null) {
				client = clientDao.trouver(Long.parseLong(idClient));
				if(client == null) throw new ServiceException("Le client n'existe pas. Id : "+idClient);
			}
			
			entreprise = entrepriseDao.trouver(Long.parseLong(id));
			
			entreprise.setNom(nom);
			entreprise.setTelephone(telephone);
			entreprise.setEmail(email);
			entreprise.setDomaine(domaine);
			entreprise.setType(type);
			entreprise.setClient(client);
			
			entrepriseDao.modifier(entreprise);
		} catch(NumberFormatException e) {
			throw new ServiceException("Le format du paramètre idEntreprise ou idClient n'est pas bon.");
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
}
