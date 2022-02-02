package CRM.forms;



import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;

import CRM.Dao.DaoException;
import CRM.Dao.UtilisateursDao;
import CRM.model.Utilisateurs;
import CRM.utils.Authentification;

public class UtilisateurForm {

	public static int CREATION=0,MODIFICATION=1;


	private String erreur = "ok";
	private int status = 200;
	private UtilisateursDao utilisateurDao;

	public UtilisateurForm(UtilisateursDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}




	public String getErreur() {
		return erreur;
	}




	public int getStatus() {
		return status;
	}




	public UtilisateursDao getUtilisateurDao() {
		return utilisateurDao;
	}




	//action == CREATION | MODIFICATION
	public Utilisateurs saveUtilisateur(JsonObject data,int action) throws NoSuchAlgorithmException, IOException {

		
		Utilisateurs utilisateur = null;

		try {
			
			String login = null;
			if (data.get("login") != null) {
				login = data.get("login").getAsString();
			}
			String email = null;
			if (data.get("email") != null) {
				email = data.get("email").getAsString();
			}
			String motDePasse = null;
			if (data.get("motDePasse") != null) {
				motDePasse = data.get("motDePasse").getAsString();
			}
			
			
			
			if (action==CREATION){
				utilisateur = new Utilisateurs();
				utilisateur.setLogin(login);
				utilisateur.setMotDePasse( Authentification.hashPass(motDePasse));
				utilisateur.setEmail(email);
			} else {
				utilisateur = utilisateurDao.trouver(email);
				utilisateur.setLogin(login);
				utilisateur.setEmail(email);
			}

			//gestion des erreurs
			if(login!=null) {
				if(login.length() <2 || login.length()>200) {
					erreur ="Le login doit être entre 2 et 200 caractères";
				}
			} else {
				erreur = "Entrer un login";
			}


			
			if (action==CREATION) {
				if (motDePasse!= null) {
					if( !motDePasse.matches( "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$" ) ) {
						erreur = "Le mot de passe doit contenir au moins 8 caractères, dont une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial." ;
					}
				}
				else {
					erreur = "Le mot de passe est obligatoire";
				}
			}
			else {
				if(!utilisateur.getMotDePasse().equals(Authentification.hashPass(motDePasse))) {
					erreur = "Le mot de passe n'est pas bon";
				}
			}
			

			
			
			if (email != null) {
				if (email.length() >60) {
					erreur = "La taille maximale de l'email est de 60 caractères.";
				}
				if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")){
					erreur = "Merci de saisir une adresse email valide.";
				}
				if (action==CREATION){
					if(utilisateurDao.existEmail(email)) {
						erreur = "Cet email existe déjà";
					}
				}
			}
			else {
				erreur = "Merci de rentrer une adresse email.";
			}

		//enrigstrement de l'utilisateur

			if(erreur.equals("ok")) {
				if(action ==CREATION) {
					utilisateurDao.ajouter(utilisateur);
				} else {
					utilisateurDao.modifier(utilisateur);
				}
			}
			else {
				status = 400;
			}
		} catch (DaoException e ) {
			status = 404;
			erreur = "Erreur Dao ...";

		} catch (NumberFormatException e ) {
			status = 404;
			erreur = "Erreur : le format de l'id n'est pas bon ...";
		}
		return utilisateur;
	}

	public Utilisateurs changePassword(JsonObject data) throws NoSuchAlgorithmException, IOException {
		Utilisateurs utilisateur = null;

		try {
			
			String email = null;
			if (data.get("email") != null) {
				email = data.get("email").getAsString();
			}
			String ancienMotDePasse = null;
			if (data.get("ancienMotDePasse") != null) {
				ancienMotDePasse = data.get("ancienMotDePasse").getAsString();
			}
			
			String nouveauMotDePasse = null;
			if (data.get("nouveauMotDePasse") != null) {
				nouveauMotDePasse = data.get("nouveauMotDePasse").getAsString();
			}

			utilisateur = utilisateurDao.trouver(email);
		
			if(utilisateur.getMotDePasse().equals(Authentification.hashPass(ancienMotDePasse))) {
				utilisateur.setMotDePasse(Authentification.hashPass(nouveauMotDePasse));
			}
			else {
				erreur = "L'ancien mot de passe n'est pas bon";
			}


			//gestion des erreurs
			if (nouveauMotDePasse!= null) {
				if( !nouveauMotDePasse.matches( "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$" ) ) {
					erreur = "Le mot de passe doit contenir au moins 8 caractères, dont une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial." ;
				}
			}
			else {
				erreur = "Le mot de passe est obligatoire";
			}

		//enrigstrement du mot de passe
			if(erreur.equals("ok")) {
				utilisateurDao.modifier(utilisateur);
			}
			else {
				status = 400;
			}
		} catch (DaoException e ) {
			status = 404;
			erreur = "Erreur Dao ...";

		} catch (NumberFormatException e ) {
			status = 404;
			erreur = "Erreur : le format de l'id n'est pas bon ...";
		}
		return utilisateur;
	}
}
