package CRM.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;
import CRM.model.Commandes;

public class CommandeForm {

	public static int CREATION=0,MODIFICATION=1;
	
	private String resultat;
	private Map <String, String> erreurs = new HashMap <String, String> ();
	
	private CommandesDao commandeDao;
	private ClientsDao clientDao;
	
	public CommandeForm (CommandesDao commandeDao) {
		this.commandeDao = commandeDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	//Gestion des erreurs
	
	public Commandes saveCommande (HttpServletRequest request,int action) {
		
		Clients client = null;
		
		Commandes commande = null;
		
		try {
			String label = getParameterOrNull(request, "label");
			String tjmHT= getParameterOrNull(request, "tjmHT");
			String dureeJours = getParameterOrNull(request, "dureeJours");
			String TVA = getParameterOrNull(request, "TVA");
			String statut = getParameterOrNull(request, "statut");
			String typeCommande = getParameterOrNull(request, "typeCommande");
			String notes = getParameterOrNull(request, "notes");
			client = clientDao.trouver(Long.parseLong(request.getParameter("client")));
			
			
			if (action == CREATION) {
				commande = new Commandes ();
			}else {
				String idCommande = request.getParameter("idCommande");
				Long id= Long.parseLong(idCommande);
				commande = commandeDao.trouver(id);
			}
			
			commande.setLabel(label);
			commande.setTjmHT(Float.parseFloat(tjmHT));
			commande.setDureeJours(Float.parseFloat(dureeJours));
			commande.setTVA(Float.parseFloat(TVA));
			commande.setStatut(statut);
			commande.setTypeCommande(typeCommande);
			commande.setNotes(notes);
			commande.setClient(client);
			
			//Gestion des erreurs
			
			//label
			 if(label != null) {
			 	if(label.length() < 2) {
			 		erreurs.put("label", "Un label doit contenir au minimum 2 caractères.");
			 	}
			 } else {
			 	erreurs.put("label", "Merci d'entrer un label.");
			 }
			
			 //tjmHT
			 if(tjmHT != null) {
				 if(tjmHT.matches("^\\d+$")) {
			 		erreurs.put("tjmht", "Veuillez rentrer des chiffres");
			 	}
			 } else {
			 	erreurs.put("tjmht", "Merci de rentrer une valeur");
			 }
			
			 //Dureejours
			 if(dureeJours != null) {
				 if(dureeJours.trim().length() > 10 ) {
			 		erreurs.put("dureeJours", "Le champ doit contenir au maximum 10 chiffres.");
				 	}
				 if (dureeJours.matches("^\\d+$")) {
					 erreurs.put("dureeJours", "Veuillez rentrer des chiffres");
				 }
			 } else {
				 	erreurs.put("dureeJours", "Merci de rentrer une valeur.");
			 }
			 
			 
			 //TVA 
			 if(TVA != null) {
			 	if(!TVA.matches("^\\d+$")) {
			 		erreurs.put("tva", "Veuillez rentrer des chiffres");
				 	}
			 } else {
				 	erreurs.put("tva", "Merci d'entrer une valeur.");
			 }
			 
			 
			 
			 //Statut
			 if(statut == null) {
		 	 	erreurs.put("statut", "Veuillez selectionner un statut");
			 }
			 
			 //TypeCommande
			 if(typeCommande == null) {
				 erreurs.put("typeCommande", "Merci de selectionner un type de commande.");
			 }
			 
			 //Notes
			 if(notes != null) {
			 	if(notes.length() > 2 || notes.length() < 200 ) {
			 		erreurs.put("notes", "Les notes contenir entre 2 et 200 caractères.");
				 	}
			 } else {
				 	erreurs.put("notes", "Merci de rentrer des notes.");
			 }
			 
			 //Clients
			 
			 if(client == null) {
				 erreurs.put("client", "Merci de selectionner un client.");
			 }
			 
	 
			
			//enrigstrement de la commande
			
			if(erreurs.isEmpty()) {
				if(action ==CREATION) {
					commandeDao.ajouter(commande);
				} else {
					commandeDao.modifier(commande);
				}
				resultat = "Commande sauvegardée !";				
			} else {
				resultat = "Echec de la sauvegarde de la commande";
				
			}
		}catch(DaoException | NumberFormatException e) {
			resultat = "Echec ajout de la commande: erreur imprévue";
			erreurs.put("DAO","Erreur imprévue..");
			e.printStackTrace();
		}
		return commande;
	}

	private static String getParameterOrNull(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if(valeur == null || valeur.trim().length()==0){
			return null;
		}
		return valeur;
		
	}
	
}
