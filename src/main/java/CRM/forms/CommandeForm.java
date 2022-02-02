package CRM.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;
import CRM.model.Commandes;
import CRM.model.Statut;
import CRM.model.TypeCommande;

public class CommandeForm {

	public static int CREATION=0,MODIFICATION=1;

	private String erreur = "Ok";
	private int status =200;
	private CommandesDao commandeDao;
	private ClientsDao clientDao;

	public CommandeForm (CommandesDao commandeDao, ClientsDao clientDao) {
		this.clientDao = clientDao;
		this.commandeDao = commandeDao;
	}
	
	public String getErreur() {
		return erreur;
	}

	public int getStatus() {
		return status;
	}

	public Commandes saveCommande (JsonObject data,int action) {

		Commandes commande = null;

		
		try {
			String label = null;
			if(data.get("label")!=null) {
				label=data.get("label").getAsString();
			}
			float tjmHT = 0;
			if(data.get("tjmHT")!=null) {
				tjmHT=data.get("tjmHT").getAsFloat();
			}
			float TVA = 0;
			if(data.get("TVA")!=null) {
				TVA=data.get("TVA").getAsFloat();
			}
			float dureeJours = 0;
			if(data.get("dureeJours")!=null) {
				dureeJours=data.get("dureeJours").getAsFloat();
			}
			Statut statut= null;
			if(data.get("statut")!=null) {
				statut=Statut.valueOf(data.get("statut").getAsString());
			}
			TypeCommande typeCommande = null;
			if(data.get("typeCommande")!=null) {
				typeCommande=TypeCommande.valueOf(data.get("typeCommande").getAsString());
			}
			String notes = null;
			if(data.get("notes")!=null) {
				notes =data.get("notes").getAsString();
			}
			Clients client = null;
			if(data.get("idClient")!=null) {
				client=clientDao.trouver(data.get("idClient").getAsLong());
			}

			if (action == CREATION) {
				commande = new Commandes ();
			}else {
				Long id= data.get("id").getAsLong();
				commande = commandeDao.trouver(id);
			}
			
			commande.setTVA(dureeJours);
			commande.setLabel(label);
			commande.setTjmHT(tjmHT);
			commande.setDureeJours(dureeJours);
			commande.setStatut(statut);
			commande.setTypeCommande(typeCommande);
			commande.setNotes(notes);
			commande.setClient(client);

			//Gestion des erreurs

			//label
			 if(label != null) {
			 	if(label.length() > 200) {
			 		erreur = "Un label doit contenir au maximum 200 caractères.";
			 	}
			 } 

			 //tjmHT
			 if(tjmHT != 0) {
				 if(tjmHT<1 ||  tjmHT > 99999999.99 ) {
					 erreur = "Le ne peut exceder cette valeur.";
					 	}
				/*if(!tjmHT.matches("^\\d+$")) {
			 		erreurs.put("tjmHT", "Veuillez rentrer des chiffres");
			 	}*/
			 } else {
				 erreur = "Merci de rentrer une valeur";
			 }

			 //Dureejours
			 if(dureeJours != 0) {
				 /*if (!dureeJours.matches("^\\d+$")) {
					 erreurs.put("dureeJours", "Veuillez rentrer des chiffres");
				 }*/
			 } else {
				 erreur = "Merci de rentrer un nombre de jours";
			 }


			 //TVA
			 if(TVA != 0) {
				 if(TVA<1 ||TVA > 99999999.99 ) {
					 erreur = "Le ne peut exceder cette valeur.";
					 	}
//				 if(!TVA.matches("^\\d+$")) {
//			 		erreurs.put("tva", "Veuillez rentrer des chiffres");
//				 	}
			 } else {
				 erreur = "Merci d'entrer une valeur.";
			 }



			 //Statut
			 if(statut == null) {
				 erreur = "Veuillez selectionner un statut";
			 }

			 //TypeCommande
			 if(typeCommande == null) {
				 erreur = "Merci de selectionner un type de commande.";
			 }

			 //Notes
			 if(notes != null) {
			 	if( notes.length() > 400 ) {
			 		erreur ="Les notes contenir au maximum 400 caractères.";
				 	}
			 } 


			 
	 
			
			//enregistrement de la commande
			
			if(erreur.equals("Ok")) {
				if(action ==CREATION) {
					commandeDao.ajouter(commande);
				} else {
					commandeDao.modifier(commande);
				}
				
			} else {
				status = 400;
			}
		}catch(DaoException e) {
			status = 404;
			erreur = "Erreur DAO";
			e.printStackTrace();
		}catch(NumberFormatException e) {
			status =400;
			erreur ="Erreur : format du paramètre n'est pas bon.";
			e.printStackTrace();
		}
		return commande;
	}


}
