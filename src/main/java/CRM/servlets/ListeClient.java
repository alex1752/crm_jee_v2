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


@WebServlet("/ListeClient")
public class ListeClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private ClientsDao clientDao;
       
  
    public ListeClient() {
        super();
        clientDao = DaoFactory.getInstance().getClientsDao();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setAttribute("client",clientDao.lister());
		}catch(DaoException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/listeClient.jsp").forward(request, response);
	}

	


}
