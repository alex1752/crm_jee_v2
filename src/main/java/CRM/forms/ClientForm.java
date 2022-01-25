package CRM.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;

public class ClientForm {

public static int CREATION=0,MODIFICATION=1;
	
	private String resultat;
	private Map<String,String> erreurs = new HashMap<String,String>();
	
	private ClientsDao clientDao;
	
	public ClientForm(ClientsDao clientDao) {
		this.clientDao=clientDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	//action == CREATION | MODIFICATION
		public Clients saveClient(HttpServletRequest request,int action) {
			
			Clients client=null;
			try {
				String nom = getParameterOrNull(request, "nom");
				String prenom = getParameterOrNull(request, "prenom");
				String telephone = getParameterOrNull(request, "telephone");
				String email = getParameterOrNull(request, "email");
				String entreprise = getParameterOrNull(request, "entreprise");
				String notes = getParameterOrNull(request, "notes");
				Boolean actif = Boolean.parseBoolean(getParameterOrNull(request, "actif"));
				
				
			
				if (action==CREATION){
					client = new Clients();
				} else {
					String idClient = request.getParameter("idClient");
					Long id= Long.parseLong(idClient);
					client = clientDao.trouver(id);
				}
				
				client.setNom(nom);
				client.setPrenom(prenom);
				client.setTelephone(telephone);
				client.setEmail(email);
				client.setEntreprise(entreprise);
				client.setActif(actif);
				client.setNotes(notes);
			
		
				//gestion des erreurs
				if(nom!=null) {
					if(nom.length() <2 || nom.length()>50) {
						erreurs.put("nom","Le nom doit être entre 2 et 50 caractères");
					}
				} else {
					erreurs.put("nnom","Entrer un nom de client");
				}
				if(prenom.length() <2 || prenom.length()>50) {
					erreurs.put("prenom","Le prenom doit être entre 2 et 50 caractères");
				}
				if(telephone!=null) {
					if(telephone.length() <4 || telephone.length()>10) {
						erreurs.put("telephone","Le numéro de téléphone  doit être entre 4 et 10 chiffres");
					}
					if(!telephone.matches("^\\d*$")) {
						erreurs.put("telephone", "le numéro de ne doit contenir que des chiffres");
					}
				} else {
					erreurs.put("telephone","Entrer un numéro de téléphone");
				}
				if(email !=null) {
					if(email.length() <4 || email.length()>200) {
						erreurs.put("email","La taille max de l'email est de 200 caractères");
					}
					if(!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
						erreurs.put("email", "Entrez une adresse mail valide");
					}
				}
				if(entreprise!=null) {
					if(entreprise.length() <2 || entreprise.length()>200) {
						erreurs.put("entreprise","Le nom d'entreprise doit être entre 2 et 200 caractères");
					}
				} else {
					erreurs.put("nnom","Entrer un nom d'entreprise");
				}
				
				if(notes!=null) {
					if(notes.length() <2 || notes.length()>2000) {
						erreurs.put("notes","La note ne doit pas dépasser 2000 caractères");
					}
				}
				
			//enrigstrement du client
			
				if(erreurs.isEmpty()) {
					if(action ==CREATION) {
						clientDao.ajouter(client);
					} else {
						clientDao.modifier(client);
					}
				resultat = "Client sauvegardé !";				
				} else {
					resultat = "Echec de la sauvegarde du client";
					
				}
			}catch(DaoException | NumberFormatException e) {
					resultat = "Echec ajout du client: erreur imprévue";
					erreurs.put("DAO","Erreur imprévue..");
					e.printStackTrace();
			}
			return client;
		}
		
		private static String getParameterOrNull(HttpServletRequest request, String nomChamp) {
			String valeur = request.getParameter(nomChamp);
			if(valeur == null || valeur.trim().length()==0){
				return null;
			}
				return valeur;
		}
}
