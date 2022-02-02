package CRM.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoException;
import CRM.Dao.DaoFactory;
import CRM.forms.CommandeForm;
import CRM.model.Commandes;
import CRM.utils.Tools;


@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommandesDao commandeDao;
	private ClientsDao clientDao;
  
    public CommandeServlet() {
        super();
        commandeDao = DaoFactory.getInstance().getCommandesDao();
        clientDao = DaoFactory.getInstance().getClientsDao();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		try {
			String idCommande = request.getParameter("idCommande");
			String idClient = request.getParameter("idClient");
			String json;
			List<Commandes> listeCommandes=null;
			
			if(idClient !=null && clientDao.trouverId(Long.parseLong(idClient))) {
				listeCommandes = commandeDao.trouverCommandesClient(Long.parseLong(idClient));
				json=new Gson().toJson(commandeDao.trouverCommandesClient(Long.parseLong(idClient)));
			}
			else if(idCommande!= null) {
				json = new Gson().toJson(commandeDao.trouver(Long.parseLong(idCommande)));
			}else {
				listeCommandes=commandeDao.lister();
				json = new Gson().toJson(commandeDao.lister());
			}
			
			response.setContentType("application/json");
			response.getWriter().write("Nombre d'éléments dans la liste : "+listeCommandes.size()+json);
			
		}catch(DaoException e) {
			response.setStatus(404); // not found
			response.getWriter().write("Erreur : DAO");
		}catch(NumberFormatException e) {
			response.setStatus(400); // bad request
			response.getWriter().write("Erreur : format du paramètre n'est pas bon.");
		}catch(Exception e) {
			response.setStatus(500); //internal error
			e.printStackTrace();
			response.getWriter().write("Erreur : problème serveur");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		
		try {
			JsonObject data = Tools.getJsonData(request);
			
			CommandeForm form = new CommandeForm(commandeDao,clientDao);
			form.saveCommande(data, CommandeForm.CREATION);
			
			response.setStatus(form.getStatus());
			response.getWriter().write(form.getErreur());
			
		}catch(Exception e) {
			response.setStatus(500); //internal error
			e.printStackTrace();
			response.getWriter().write("Erreur : problème serveur");
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		
		try {
			JsonObject data = Tools.getJsonData(request);
			
			CommandeForm form = new CommandeForm(commandeDao,clientDao);
			form.saveCommande(data, CommandeForm.MODIFICATION);
			
			response.setStatus(form.getStatus());
			response.getWriter().write(form.getErreur());
		}catch(Exception e) {
			response.setStatus(500); //internal error
			e.printStackTrace();
			response.getWriter().write("Erreur : problème serveur");
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		try {
			String idCommande=request.getParameter("idCommande");
			
			commandeDao.supprimer(Long.parseLong(idCommande));
			
			response.getWriter().write("Commande supprimee");
			
		}catch(DaoException e) {
			response.setStatus(404); // not found
			response.getWriter().write("Erreur : la commande n'existe pas");
		}catch(NumberFormatException e) {
			response.setStatus(400); // bad request
			response.getWriter().write("Erreur : format du paramètre n'est pas bon.");
		}catch(Exception e) {
			response.setStatus(500); //internal error
			e.printStackTrace();
			response.getWriter().write("Erreur : problème serveur");
		}
	
	}
}
