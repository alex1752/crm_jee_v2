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


@WebServlet("/SupprimerClient")
public class SupprimerClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientsDao clientDao;
    
    public SupprimerClient() {
        super();
        clientDao =DaoFactory.getInstance().getClientsDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idClient=request.getParameter("idClient");
		
		try {
			Long id = Long.parseLong(idClient);
			clientDao.supprimer(id);
		}catch(NumberFormatException | DaoException e) {	
			e.printStackTrace();
		}
			
		response.sendRedirect(request.getContextPath() + "/ListeClient");
	
		}


}
