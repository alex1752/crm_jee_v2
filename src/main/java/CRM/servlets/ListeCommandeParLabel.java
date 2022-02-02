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

import CRM.Dao.ClientsDao;
import CRM.Dao.CommandesDao;
import CRM.Dao.DaoFactory;
import CRM.model.Commandes;
import CRM.Dao.DaoException;
import CRM.utils.Tools;


@WebServlet("/ListeCommandeParLabel")
public class ListeCommandeParLabel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommandesDao commandeDao;
	private ClientsDao clientDao; 
	
    public ListeCommandeParLabel() {
        super();
        commandeDao = DaoFactory.getInstance().getCommandesDao();
        clientDao = DaoFactory.getInstance().getClientsDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		response.setCharacterEncoding("UTF-8");
		String json;
		List<Commandes> listeCommandes=null;
		
		try {
			JsonObject data = Tools.getJsonData(request);
	
			String label=null;
			if(data.get("label") !=null) {
				label=data.get("label").getAsString();
			}

			if(label != null && commandeDao.listerParLabel(label).contains(label)) {	 
				json = new Gson().toJson(commandeDao.listerParLabel(label));
				listeCommandes=commandeDao.listerParLabel(label);
			}else {
				json = "Veuillez saisir un label";
			}
			response.setContentType("application/json");
			response.getWriter().write("Nombre d'éléments dans la liste : "+listeCommandes.size()+json);
			
			
		}catch(DaoException e) {
			response.setStatus(404); // not found
			response.getWriter().write("Erreur : DAO");
			e.printStackTrace();
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
