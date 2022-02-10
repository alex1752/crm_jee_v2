package CRM.services;

import java.util.List;

import com.google.gson.JsonObject;

import CRM.Dao.DaoException;
import CRM.Dao.ModificationsDao;
import CRM.Dao.UtilisateursDao;
import CRM.model.Clients;
import CRM.model.Commandes;
import CRM.model.Modification;
import CRM.model.Utilisateurs;

public class ServiceModification {

	private ModificationsDao dao;
	private UtilisateursDao daoUtilisateur;
	
	public ServiceModification() {
		dao=new ModificationsDao();
		daoUtilisateur = new UtilisateursDao();
	}
	
	public String lister() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(dao.lister());	
	}
	
	public String listerParUtilisateur(long idUtilisateur) throws ServiceException {
		
		Utilisateurs utilisateur = daoUtilisateur.trouver(idUtilisateur);
		if(utilisateur == null)
			throw new ServiceException("L'utilisateur n'existe pas. Id : "+idUtilisateur);
		
		List<Modification> modifications = dao.listerParUtilisateur(idUtilisateur);
		
		if(modifications.isEmpty())
			throw new ServiceException("L'utilisateur d' Id : "+idUtilisateur+ " n'a pas fait de modification.");
		
		return ServiceTools.getSuperJson().toJson(modifications);
	}
	
	public void ajouter(Long idUtilisateur, Long idObjet, String className,String etat) throws ServiceException {
		String historique = null;
		Utilisateurs utilisateur = null;

		try {
			historique ="L'utilisateur d'id:" + idUtilisateur +" a " +etat+" l'objet : "+ className +" id("+idObjet+")";
			utilisateur = daoUtilisateur.trouver(idUtilisateur);
			Modification modification = new Modification(utilisateur,historique);
			
			dao.ajouter(modification);
			
		}catch(DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
}
