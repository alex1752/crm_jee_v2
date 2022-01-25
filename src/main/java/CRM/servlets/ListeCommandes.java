package CRM.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.CommandesDao;
import CRM.Dao.DaoFactory;
import CRM.Dao.DaoException;

/**
 * Servlet implementation class ListeCommandes
 */
@WebServlet("/listeCommandes")
public class ListeCommandes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommandesDao commandesDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeCommandes() {
        super();
        commandesDao = DaoFactory.getInstance().getCommandesDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setAttribute("commandes", commandesDao.lister());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/listeCommandes.jsp").forward(request, response);
	}
}
