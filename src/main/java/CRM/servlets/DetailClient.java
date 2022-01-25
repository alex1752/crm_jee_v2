package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;


@WebServlet("/DetailClient")
public class DetailClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientsDao clientDao;
	private CommandesDao commandeDao;
    
    public DetailClient() {
        super();
        clientDao = DaoFactory.getInstance().getClientsDao();
        commandeDao=DaoFactory.getInstance().getCommandesDao();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idClient=request.getParameter("idClient");
		
		try {
			Long id= Long.parseLong(idClient);
			request.setAttribute("client", clientDao.trouver(id));
			
//			listeCommande = commandeDao.listerParClient(id);
//			request.setAttribute("commandes", listeCommande);
//			request.setAttribute("nbcommandes", listeCommande.size());
		}catch(NumberFormatException | DaoException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/detailClient.jsp").forward(request, response);
	}


}