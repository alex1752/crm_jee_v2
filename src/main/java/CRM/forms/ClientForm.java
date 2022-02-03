package CRM.forms;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.JsonObject;
import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.model.Clients;

public class ClientForm {

public static int CREATION=0,MODIFICATION=1;

	private String erreur = "ok";
	private int status = 200;
	private ClientsDao clientDao;

	public ClientForm(ClientsDao clientDao) {
		this.clientDao=clientDao;
	}

	public String getErreur() {
		return erreur;
	}

	public int getStatus() {
		return status;
	}



		//action == CREATION | MODIFICATION
		public Clients saveClient(JsonObject data, int action) {

			Clients client=null;


			try {
				String nom = null;
				if (data.get("nom") != null) {
					 nom = data.get("nom").getAsString();
				}
				
				String prenom = null;
				if (data.get("prenom") != null) {
					 prenom = data.get("prenom").getAsString();
				}
				String telephone = null;
				if (data.get("telephone") != null) {
					 telephone = data.get("telephone").getAsString();
				}
				String email = null;
				if (data.get("email") != null) {
					 email = data.get("email").getAsString();
				}
				
				String entreprise = null;
				if (data.get("entreprise") != null) {
					 entreprise = data.get("entreprise").getAsString();
				}
				
				Boolean actif = null ;
				if (data.get("actif") != null) {
					 actif = data.get("actif").getAsBoolean();
				}
				
				String notes = null;
				if (data.get("email") != null) {
					 notes = data.get("notes").getAsString();
				}



				if (action==CREATION){
					client = new Clients();
				} else {
					Long id= data.get("id").getAsLong();
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
				
				//Nom
				if(nom!=null) {
					if(nom.length() <2 || nom.length()>50) {
						erreur="Le nom doit être entre 2 et 50 caractères";
					}
				} else {
					erreur = "Merci de rentrer un nom.";
				}
				
				//Prénom
				if(prenom.length() <2 || prenom.length()>50) {
					erreur = "Le prenom doit être entre 2 et 50 caractères";
				}
				
				//Téléphone
				if(telephone!=null) {
					if(telephone.length() <4 || telephone.length()>10) {
						erreur = "Le numéro de téléphone  doit être entre 4 et 10 chiffres";
					}
					if(!telephone.matches("^\\d*$")) {
						erreur= "le numéro doit contenir uniquement des chiffres";
					}
				} else {
					erreur = "Entrer un numéro de téléphone";
				}
				
				if(email !=null) {
					if(email.length() <4 || email.length()>200) {
						erreur = "La taille max de l'email est de 200 caractères";
					}
					if(!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
						erreur = "Entrez une adresse mail valide";
					}
					if (action==CREATION){
						
						if (clientDao.existEmail(email)){
							erreur = "Adresse email déjà utilisée";
						}
					}else {
					
						if (clientDao.trouver(client.getId()).getEmail() != email){
							if (clientDao.existEmail(email)){
								erreur = "Adresse email déjà utilisée";
							}
						}
					}

				}


				if(entreprise!=null) {
					if(entreprise.length() <2 || entreprise.length()>200) {
						erreur = "Le nom d'entreprise doit être entre 2 et 200 caractères";
					}
				}
				if(notes!=null) {
					if(notes.length() <2 || notes.length()>2000) {
						erreur = "La note ne doit pas dépasser 2000 caractères";
					}
				}

			//enregistrement du client

				if (erreur.equals("ok")){
					if (action == CREATION) {
						clientDao.ajouter(client);
					}else {
						clientDao.modifier(client);	
					}
				} else {
					status = 400;
				}
					
			} catch (DaoException e) {
				erreur="Erreur imprévue...";
				status = 404;
			}catch (NumberFormatException e) {
				erreur= "le formmat de l'id est invalide";
				status = 400;
			}
				
			return client;

		
		}
		
}