package CRM.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoFactory;
import CRM.Dao.DaoException;
import CRM.Dao.CommandesDao;
import CRM.model.Clients;
import CRM.model.Commandes;

/**
 * Servlet implementation class CreationCommandes
 */
@WebServlet("/creationCommandes")
public class CreationCommandes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommandesDao commandesDao;
	private ClientsDao clientsDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationCommandes() {
        super();
        commandesDao = DaoFactory.getInstance().getCommandesDao();
        clientsDao = DaoFactory.getInstance().getClientsDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("clients", clientsDao.lister());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/creationCommandes.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Déclaration des variables
		String resultat = "";
		Map<String, String> erreurs = new HashMap<String, String>();
		Commandes commande = null;
		Clients client = null;
		String label = null, categorie = null, statut = null, typeCommande = null, notes = null;
		float tjmHT = 0, dureeJours= 0, TVA = 0;
		
		try {
			//Récupération des données et instanciation de la commande
			if(request.getParameter("label") != null && request.getParameter("label").trim().length() > 0) {
				label = request.getParameter("label");
			}
			if(request.getParameter("tjmht") != null && request.getParameter("tjmht").trim().length() > 0) {
				tjmHT = Float.parseFloat(request.getParameter("tjmht"));
			}
            if(request.getParameter("dureejours") != null && request.getParameter("dureejours").trim().length() > 0) {
				dureeJours = Float.parseFloat(request.getParameter("dureejours"));
			}
            if(request.getParameter("tva") != null && request.getParameter("tva").trim().length() > 0) {
				TVA = Float.parseFloat(request.getParameter("tva"));
			}
            if(request.getParameter("statut") != null && request.getParameter("statut").trim().length() > 0) {
				statut = request.getParameter("statut");
			}
            if(request.getParameter("typecommande") != null && request.getParameter("typecommande").trim().length() > 0) {
				typeCommande = request.getParameter("typecommande");
			}
            if(request.getParameter("notes") != null && request.getParameter("notes").trim().length() > 0) {
				notes = request.getParameter("notes");
			}
			
			
			String idClient = request.getParameter("clients");
			Long id = Long.parseLong(idClient);
			client = clientsDao.trouver(id);
		
	
			commande = new Commandes(label, tjmHT, dureeJours, TVA, statut, typeCommande, notes, client);
	
			
			//Gestion des erreurs
			// if(titre != null) {
			// 	if(titre.length() < 2) {
			// 		erreurs.put("titreLivre", "Un titre de livre doit contenir au minimum 2 caractères.");
			// 	}
			// } else {
			// 	erreurs.put("titreLivre", "Merci d'entrer un titre.");
			// }
			
			// if(categorie != null) {
			// 	if(categorie.length() < 2) {
			// 		erreurs.put("categorieLivre", "Une catégorie doit contenir au minimum 2 caractères.");
			// 	}
			// } else {
			// 	erreurs.put("categorieLivre", "Merci d'entrer une catégorie.");
			// }
			
			
			//Enregistrement du client
			if(erreurs.isEmpty()) {
				commandesDao.ajouter(commande);
				resultat = "Commande ajoutée avec succès.";
			} else {
				resultat = "Echec de l'ajout de la commande.";
				request.setAttribute("commande", commande);
			}
		} catch (DaoException e) {
			resultat = "Erreur imprévue lors de la création.";
			request.setAttribute("commande", commande);
		}
		
		request.setAttribute("erreurs", erreurs);
		request.setAttribute("resultat", resultat);
		
		doGet(request, response);
	}

}
