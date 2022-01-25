package CRM.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoFactory;
import CRM.forms.ClientForm;
import CRM.model.Clients;


@WebServlet("/AjouterClient")
public class AjouterClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientsDao clientDao;
  
    public AjouterClient() {
        super();
        clientDao = DaoFactory.getInstance().getClientsDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterClient.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ClientForm form = new ClientForm(clientDao);
		Clients client = form.saveClient(request, ClientForm.CREATION);
	
		if(form.getErreurs().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/detailClient?idClient=" + client.getId() );
		}else {
			request.setAttribute("client",client);
			request.setAttribute("form",form);
			this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterClient.jsp").forward(request, response);
		}
	}
}
