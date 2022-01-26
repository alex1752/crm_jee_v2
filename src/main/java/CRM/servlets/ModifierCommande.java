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


/**
 * Servlet implementation class ModificationCommandes
 */
@WebServlet("/ModifierCommande")
public class ModifierCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommandesDao commandesDao;
	private ClientsDao clientsDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierCommande() {
        super();
        commandesDao = DaoFactory.getInstance().getCommandesDao();
        clientsDao = DaoFactory.getInstance().getClientsDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/modifierCommande.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CommandeForm form = new CommandeForm (commandesDao);
		Commandes commande = form.saveCommande (request, CommandeForm.MODIFICATION);
		
		if (form.getErreurs().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ListeCommande");
		}else {
			request.setAttribute("commande",  commande);
			request.setAttribute("form",  form);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/modifierCommande.jsp").forward(request, response);
		}

	}
}
