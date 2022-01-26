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
import CRM.forms.CommandeForm;
import CRM.Dao.DaoException;
import CRM.Dao.CommandesDao;
import CRM.model.Clients;
import CRM.model.Commandes;
import CRM.model.Statut;
import CRM.model.TypeCommande;

/**
 * Servlet implementation class CreationCommandes
 */
@WebServlet("/AjouterCommande")
public class AjouterCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommandesDao commandesDao;
	private ClientsDao clientsDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterCommande() {
        super();
        commandesDao = DaoFactory.getInstance().getCommandesDao();
        clientsDao = DaoFactory.getInstance().getClientsDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("types", TypeCommande.values());
		request.setAttribute("stat", Statut.values());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterCommande.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		CommandeForm form = new CommandeForm (commandesDao);
		Commandes commande = form.saveCommande (request, CommandeForm.CREATION);
		
		if (form.getErreurs().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ListeCommande");
		}else {
			request.setAttribute("commande",  commande);
			request.setAttribute("form",  form);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterCommande.jsp").forward(request, response);
		}
	}

}
