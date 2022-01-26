package CRM.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import CRM.Dao.DaoException;
import CRM.Dao.UtilisateursDao;
import CRM.model.Utilisateurs;

public class UtilisateurForm {

	public static int CREATION=0,MODIFICATION=1;


	private String resultat;
	private Map<String,String> erreurs = new HashMap<>();
	private UtilisateursDao utilisateurDao;

	public UtilisateurForm(UtilisateursDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	//action == CREATION | MODIFICATION
	public Utilisateurs saveUtilisateur(HttpServletRequest request,int action) {

		
		Utilisateurs utilisateur = null;




		try {
			String login = getParameterOrNull(request, "loginUtilisateur");
			String motDePasse = getParameterOrNull(request, "motDePasseUtilisateur");
			String email = getParameterOrNull(request, "emailUtilisateur");

			if (action==CREATION){
				utilisateur = new Utilisateurs();
			} else {
				String idUtilisateur = request.getParameter("idUtilisateur");
				Long id= Long.parseLong(idUtilisateur);
				utilisateur = utilisateurDao.trouver(id);
			}

			utilisateur.setLogin(login);
			utilisateur.setMotDePasse(motDePasse);
			utilisateur.setEmail(email);



			//gestion des erreurs
			if(login!=null) {
				if(login.length() <2 || login.length()>200) {
					erreurs.put("loginUtilisateur","Le login doit être entre 2 et 200 caractères");
				}
			} else {
				erreurs.put("loginUtilisateur","Entrer un nom d'utilisateur");
			}

			if(motDePasse!=null) {
				if(motDePasse.length() <8 || motDePasse.length()>200) {
					erreurs.put("motDePasseUtilisateur","Le mot de passe doit être entre 8 et 200 caractères");
				}
			} else {
				erreurs.put("motDePasseUtilisateur","Entrer un mot de passe");
			}

			if(email !=null) {
				if(email.length() <4 || email.length()>200) {
					erreurs.put("emailUtilisateur","La taille max de l'email est de 200 caractères");
				}
				if(!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
					erreurs.put("emailUtilisateur", "Entrez une adresse mail valide");
				}
				if (action==CREATION){
					for (Utilisateurs u : utilisateurDao.lister()) {
						if (email.equals(u.getEmail())){
							erreurs.put("emailUtilisateur", "Adresse email déjà utilisée");
						}
					}
				}
				else {
					for (Utilisateurs u : utilisateurDao.lister()) {
						String idUtilisateur = request.getParameter("idUtilisateur");
						Long id= Long.parseLong(idUtilisateur);
						if (email.equals(u.getEmail()) && u.getId()!=id){
							erreurs.put("emailUtilisateur", "Adresse email déjà utilisée");
						}
					}
				}
			}

		//enrigstrement de l'utilisateur

			if(erreurs.isEmpty()) {
				if(action ==CREATION) {
					utilisateurDao.ajouter(utilisateur);
				} else {
					utilisateurDao.modifier(utilisateur);
				}
			resultat = "Utilisateur sauvegardé !";
			} else {
				resultat = "Echec de la sauvegarde de l'utilisateur";

			}
		}catch(DaoException | NumberFormatException e) {
				resultat = "Echec ajout de l'utilisateur: erreur imprévue";
				erreurs.put("DAO","Erreur imprévue..");
				e.printStackTrace();
		}
		return utilisateur;
	}

	private static String getParameterOrNull(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if(valeur == null || valeur.trim().length()==0){
			return null;
		}
			return valeur;
	}

}
