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
import CRM.forms.ClientForm;
import CRM.forms.UtilisateurForm;
import CRM.model.Utilisateurs;


@WebServlet("/ModifierUtilisateur")
public class ModifierUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateursDao utilisateurDao;


    public ModifierUtilisateur() {
        super();
        utilisateurDao = DaoFactory.getInstance().getUtilisateurDao();

    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idUtilisateur=request.getParameter("idUtilisateur");

		try {
			Long id= Long.parseLong(idUtilisateur);
			request.setAttribute("utilisateur", utilisateurDao.trouver(id));
		}catch(NumberFormatException | DaoException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/modifierUtilisateur.jsp").forward(request, response);

	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UtilisateurForm form = new UtilisateurForm(utilisateurDao);
		Utilisateurs utilisateur = form.saveUtilisateur(request, ClientForm.MODIFICATION);

		if(form.getErreurs().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ListeUtilisateur");
		}else {
			request.setAttribute("utilisateur",utilisateur);
			request.setAttribute("form",form);
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifierUtilisateur.jsp").forward(request, response);
		}
	}

}
