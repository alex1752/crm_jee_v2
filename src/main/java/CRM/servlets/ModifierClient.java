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
import CRM.forms.ClientForm;
import CRM.model.Clients;


@WebServlet("/ModifierClient")
public class ModifierClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientsDao clientDao;

    public ModifierClient() {
        super();
        clientDao = DaoFactory.getInstance().getClientsDao();
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idClient=request.getParameter("idClient");

		try {
			Long id= Long.parseLong(idClient);
			request.setAttribute("client", clientDao.trouver(id));
		}catch(NumberFormatException | DaoException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/modifierClient.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ClientForm form = new ClientForm(clientDao);
		Clients client = form.saveClient(request, ClientForm.MODIFICATION);

		if(form.getErreurs().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/DetailClient?idClient=" + client.getId() );
		}else {
			request.setAttribute("client",client);
			request.setAttribute("form",form);
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifierClient.jsp").forward(request, response);
		}
	}

}
