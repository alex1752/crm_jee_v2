package CRM.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import CRM.Dao.DaoException;
import CRM.Dao.UtilisateursDao;
import CRM.model.Utilisateurs;
import CRM.utils.Authentification;
import CRM.utils.TokenJWT;





public class ServiceUtilisateur {

	
	private UtilisateursDao utilisateurDao;
	
	public ServiceUtilisateur() {
		this.utilisateurDao = new UtilisateursDao();
	}
	
	// La suppression des mots de passe pour l'affichage est effectué dans l'adapter
	
	public String lister() throws ServiceException {
		return ServiceTools.getSuperJson().toJson(utilisateurDao.lister());

	}
	
	public String trouver(long id) throws ServiceException {
		Utilisateurs utilisateur = utilisateurDao.trouver(id); 
		if (utilisateur == null) throw new ServiceException("L'utilisateur n'existe pas. Id : "+id);
		return ServiceTools.getSuperJson().toJson(utilisateur);
	}
	
	public String trouver(String email) throws ServiceException {
		Utilisateurs utilisateur = utilisateurDao.trouver(email); 
		if (utilisateur == null) throw new ServiceException("L'utilisateur n'existe pas. email : "+ email);
		return ServiceTools.getSuperJson().toJson(utilisateur);
	}
	
	// ====================================
	
	public String login(JsonObject data) throws ServiceException, NoSuchAlgorithmException, IOException {		
		String email = null, motDePasse = null;
		Utilisateurs utilisateur = null;
		
		email = ServiceTools.getStringParameter(data, "emailUtilisateur", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
		motDePasse = ServiceTools.getStringParameter(data, "motDePasseUtilisateur", 1, 200, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");		
		if(email == null) throw new ServiceException("Le champ emailUtilisateur est obligatoire.");
		if(motDePasse == null) throw new ServiceException("Le champ motDePasseUtilisateur est obligatoire.");
		
		
		utilisateur = utilisateurDao.trouver(email, Authentification.hashPass(motDePasse));
		if(utilisateur == null) throw new ServiceException("Les identifiants ne sont pas correct.");
			
		
		String token = TokenJWT.generateJWT(email, 60);

		JsonObject userJson = new JsonObject();
		userJson.addProperty("login", utilisateur.getLogin());
		userJson.addProperty("email", utilisateur.getEmail());
		
		JsonObject dataReturn = new JsonObject();
		dataReturn.addProperty("token", token); // addProperty : pour un string 
		dataReturn.add("utilisateur", userJson); // add : pour un objet json
					
		return dataReturn.toString();
	}
	
	
	public void ajouter(JsonObject data) throws ServiceException, NoSuchAlgorithmException, IOException {
		String login = null, motDePasse = null, email = null;
		
		try {
			login = ServiceTools.getStringParameter(data, "loginUtilisateur", 1, 200);	
			motDePasse = ServiceTools.getStringParameter(data, "motDePasseUtilisateur", 1, 200, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");	
			email = ServiceTools.getStringParameter(data, "emailUtilisateur", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			
			if (login == null) throw new ServiceException("Le champ loginUtilisateur est obligatoire");
			if (motDePasse == null) throw new ServiceException("Le champ motDePasseUtilisateur est obligatoire");
			if (email == null) throw new ServiceException("Le champ emailUtilisateur est obligatoire");
			
			utilisateurDao.ajouter(new Utilisateurs(login, Authentification.hashPass(motDePasse), email));
			
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	
	public void modifierLogin(JsonObject data) throws ServiceException, NoSuchAlgorithmException, IOException {
		String login = null, motDePasse = null, email = null;
		Utilisateurs utilisateur = null;
		
		try {
			email = ServiceTools.getStringParameter(data, "emailUtilisateur", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			login = ServiceTools.getStringParameter(data, "loginUtilisateur", 1, 200);	
			motDePasse = ServiceTools.getStringParameter(data, "motDePasseUtilisateur", 1, 200, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");	
			
			if (login == null) throw new ServiceException("Le champ loginUtilisateur est obligatoire");
			if (motDePasse == null) throw new ServiceException("Le champ motDePasseUtilisateur est obligatoire");
			if (email == null) throw new ServiceException("Le champ emailUtilisateur est obligatoire");
			
			// Récupération de l'utilisateur avec l'email
			utilisateur = utilisateurDao.trouver(email);
			if(utilisateur == null) throw new ServiceException("\"L'utilisateur n'existe pas. email : " + email);
			
			// Test du mot de Passe
			if(utilisateur.getMotDePasse().equals(Authentification.hashPass(motDePasse))) throw new ServiceException("Mot de passe incorrect");
			
			// Modification
			utilisateur.setLogin(login);
			utilisateurDao.modifier(utilisateur);
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}

	public void modifierMotDePasse(JsonObject data) throws ServiceException, NoSuchAlgorithmException, IOException {
		String email = null, ancienMotDePasse = null, nouveauMotDePasse = null;
		Utilisateurs utilisateur = null;
		
		try {
			email = ServiceTools.getStringParameter(data, "emailUtilisateur", 1, 200, "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");	
			ancienMotDePasse = ServiceTools.getStringParameter(data, "ancienMotDePasseUtilisateur", 1, 200, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
			nouveauMotDePasse = ServiceTools.getStringParameter(data, "nouveauMotDePasseUtilisateur", 1, 200, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");	
			
			if (ancienMotDePasse == null) throw new ServiceException("Le champ ancienMotDePasseUtilisateur est obligatoire.");
			if (nouveauMotDePasse == null) throw new ServiceException("Le champ nouveauMotDePasseUtilisateur est obligatoire");
			
			// Récupération de l'utilisateur avec l'email
			utilisateur = utilisateurDao.trouver(email);
			if(utilisateur == null) throw new ServiceException("L'utilisateur n'existe pas. email : " + email);

			// Test avec l'ancien mot de Passe
			if(utilisateur.getMotDePasse().equals(Authentification.hashPass(ancienMotDePasse))) throw new ServiceException("Votre ancien mot de passe ne correspond pas à votre saisie");
			
			// Modification
			utilisateur.setMotDePasse(Authentification.hashPass(nouveauMotDePasse));
			utilisateurDao.modifier(utilisateur);
		} catch (DaoException e) {
			throw new ServiceException("Erreur DAO.");
		}
	}
	
	
}
