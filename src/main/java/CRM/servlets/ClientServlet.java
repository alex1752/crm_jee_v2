package CRM.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import CRM.Dao.ClientsDao;
import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.forms.ClientForm;
import CRM.model.Clients;
import CRM.utils.Tools;


@WebServlet("/Client")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientsDao clientDao;

//Ajouter
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding ("UTF-8");
		
		try {
			
			JsonObject data = Tools.getJsonData(request);
			
			ClientForm form = new ClientForm(clientDao);
			form.saveClient(data, ClientForm.CREATION);
	
			response.setStatus(form.getStatus());
			response.getWriter().write(form.getErreur());
		
		}catch (Exception e) {
		e.printStackTrace();
		response.setStatus(500);
		response.getWriter().write("Erreur: Problème lié au serveur");
		}

	}
	
//Lister
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding ("UTF-8");
		String json;
		
		try {
			
			String idClient = request.getParameter("idClient");
			System.out.println(idClient);
			
			if(idClient != null) { 
				json = new Gson().toJson(clientDao.trouver(Long.parseLong(idClient)));

			} else {
				json = new Gson().toJson(clientDao.lister());
			}
			
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (NumberFormatException e) {
			response.setStatus(400);
			response.getWriter().write("Erreur: Le format du paramètre n'est pas bon");
		} catch (DaoException e) {
			response.setStatus(400);
			response.getWriter().write("Erreur: DAO");
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write("Erreur: Problème lié au serveur");
		}
		
	}
		
//Modifier
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			response.setCharacterEncoding ("UTF-8");
			
		try {
			
			JsonObject data = Tools.getJsonData(request);
			
			ClientForm form = new ClientForm(clientDao);
			form.saveClient(data, ClientForm.MODIFICATION);
	
			response.setStatus(form.getStatus());
			response.getWriter().write(form.getErreur());
		}catch (Exception e) {
			
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write("Erreur: Problème lié au serveur");
			
		}
		
	}
	
//Supprimer

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding ("UTF-8");
		
		try {
			
			
			String idClient = request.getParameter("idClient");
			
			clientDao.supprimer(Long.parseLong(idClient));
			
			response.getWriter().write("ok");
			
		} catch (NumberFormatException e) {
			response.setStatus(400);
			response.getWriter().write("Erreur: Le format du paramètre n'est pas bon");
		} catch (DaoException e) {
			response.setStatus(400);
			response.getWriter().write("Erreur: DAO");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write("Erreur: Problème lié au serveur");
		}
		
	}
	
}
