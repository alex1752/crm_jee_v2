package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.Dao.UtilisateursDao;


@WebServlet("/ListeUtilisateur")
public class ListeUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UtilisateursDao utilisateurDao;

    public ListeUtilisateur() {
        super();
        utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("utilisateur",utilisateurDao.lister());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/listeUtilisateur.jsp").forward(request, response);
	}


}
