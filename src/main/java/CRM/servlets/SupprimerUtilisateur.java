package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.Dao.UtilisateursDao;


@WebServlet("/SupprimerUtilisateur")
public class SupprimerUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateursDao utilisateurDao;


    public SupprimerUtilisateur() {
        super();
        utilisateurDao =DaoFactory.getInstance().getUtilisateurDao();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUtilisateur=request.getParameter("idUtilisateur");
		
		try {
			Long id = Long.parseLong(idUtilisateur);
			utilisateurDao.supprimer(id);
		}catch(NumberFormatException | DaoException e) {	
			e.printStackTrace();
		}
			
		response.sendRedirect(request.getContextPath() + "/ListeUtilisateur");
	
	}

}
