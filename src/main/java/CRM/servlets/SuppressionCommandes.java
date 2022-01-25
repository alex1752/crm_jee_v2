package CRM.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.DaoFactory;
import CRM.Dao.DaoException;
import CRM.Dao.CommandesDao;

/**
 * Servlet implementation class SuppressionCommandes
 */
@WebServlet("/suppressionCommandes")
public class SuppressionCommandes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommandesDao commandesDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuppressionCommandes() {
        super();
        commandesDao = DaoFactory.getInstance().getCommandesDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idCommande = request.getParameter("idCommande");
		
		try {
			Long id = Long.parseLong(idCommande);
			commandesDao.supprimer(id);
		} catch (NumberFormatException | DaoException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/listeCommandes");
	}

}
