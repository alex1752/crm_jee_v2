package CRM.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;
import CRM.model.Commandes;
import CRM.model.Statut;
import CRM.model.TypeCommande;

public class CommandeForm {

	public static int CREATION=0,MODIFICATION=1;

	private String resultat;
	private Map <String, String> erreurs = new HashMap <> ();

	private CommandesDao commandeDao;
	private ClientsDao clientDao;

	public CommandeForm (CommandesDao commandeDao, ClientsDao clientDao) {
		this.clientDao = clientDao;
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
			String idClient = getParameterOrNull(request,"clients");
			
			System.out.println(idClient);
			
			if (action == CREATION) {
				commande = new Commandes ();
			}else {
				String idCommande = request.getParameter("idCommande");
				Long id= Long.parseLong(idCommande);
				commande = commandeDao.trouver(id);
			}

			if (tjmHT != null) {
				commande.setTjmHT(Float.parseFloat(tjmHT));
			}
			if(TVA != null){
				commande.setTVA(Float.parseFloat(TVA));
			}
			if (dureeJours != null) {
				commande.setDureeJours(Float.parseFloat(dureeJours));
			}
			 if(idClient != null) {
					client = clientDao.trouver(Long.parseLong(idClient));
					commande.setClient(client);
			 }
			 else {
			 		erreurs.put("clients", "Choisissez un client");
			 }
			
			commande.setLabel(label);
			commande.setStatut(Statut.valueOf(statut));
			commande.setTypeCommande(TypeCommande.valueOf(typeCommande));
			commande.setNotes(notes);

			//Gestion des erreurs

			//label
			 if(label != null) {
			 	if(label.length() > 200) {
			 		erreurs.put("label", "Un label doit contenir au maximum 200 caractères.");
			 	}
			 } 

			 //tjmHT
			 if(tjmHT != null) {
				 if(tjmHT.length()<1 ||  tjmHT.trim().length() > 10 ) {
				 		erreurs.put("tjmHT", "Le champ doit contenir entre 1 et 10 chiffres.");
					 	}
				/*if(!tjmHT.matches("^\\d+$")) {
			 		erreurs.put("tjmHT", "Veuillez rentrer des chiffres");
			 	}*/
			 } else {
			 	erreurs.put("tjmHT", "Merci de rentrer une valeur");
			 }

			 //Dureejours
			 if(dureeJours != null) {
				 if(dureeJours.length()<1 || dureeJours.trim().length() > 10 ) {
			 		erreurs.put("dureeJours", "Le champ doit contenir entre 1 et 10 chiffres.");
				 	}
				 /*if (!dureeJours.matches("^\\d+$")) {
					 erreurs.put("dureeJours", "Veuillez rentrer des chiffres");
				 }*/
			 } else {
				 	erreurs.put("dureeJours", "Merci de rentrer une valeur.");
			 }


			 //TVA
			 if(TVA != null) {
				 if(TVA.length()<1 ||TVA.trim().length() > 10 ) {
				 		erreurs.put("TVA", "Le champ doit contenir entre 1 et 10 chiffres.");
					 	}
				 /*if(!TVA.matches("^\\d+$")) {
			 		erreurs.put("tva", "Veuillez rentrer des chiffres");
				 	}*/
			 } else {
				 	erreurs.put("TVA", "Merci d'entrer une valeur.");
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
			 	if( notes.length() > 400 ) {
			 		erreurs.put("notes", "Les notes contenir au maximum 400 caractères.");
				 	}
			 } 


			 
	 
			
			//enregistrement de la commande
			
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
