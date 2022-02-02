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
import utils.Tools;

/**
 * Servlet implementation class ListerClientParNom
 */
@WebServlet("/ListerClientParNom")
public class ListerClientParNom extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientsDao clientDao;
    
  public ListerClientParNom() {
        super();
        clientDao = DaoFactory.getInstance().getClientsDao();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding ("UTF-8");
		String json;
		
		try {
			
			JsonObject data = Tools.getJsonData(request);
			
			String nom = null;
			if(data.get("nom") != null) {
			nom = data.get("nom").getAsString();
			}
			
			if (nom != null) {
				json = new Gson().toJson(clientDao.listerParNom(nom));
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

	

}
