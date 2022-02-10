package CRM.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import CRM.model.Clients;

public class ClientsDao extends DaoObject<Clients> {

	public ClientsDao() {
		super(Clients.class);
	}

//	public Clients trouverId(long id) {
//		Clients object = null;
//
//		try {
//			object = getFactory().getEntityManager().find(Clients.class, id);
//			
//		} catch(NoResultException e) {
//			e.printStackTrace();
//		} finally {
//			getFactory().releaseEntityManager();
//		}
//		
//		return object;
//	}
	
	public List<Clients> listerParNom(String nom) {
		List<Clients> objects = null;
		
		try {
			EntityManager em= getFactory().getEntityManager();
			Query query = em.createQuery("FROM "+ Clients.class.getCanonicalName() +" WHERE nom LIKE CONCAT('%',?1,'%')", Clients.class);
			objects = query.setParameter(1, nom).getResultList();
			
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return objects;
	}
	
	public Boolean existEmail(String email, Long id) {
		Clients client = null;
		Boolean emailExist = false;
		
		try {
			Query query = getFactory().getEntityManager().createQuery("FROM Clients c WHERE c.email LIKE :email AND u.id != :id", Clients.class);
			query.setParameter("email", email);
			query.setParameter("id", id);
			client =  (Clients) query.getSingleResult();	
			
			// Si la requête ne renvoit rien on rentre dans le catch sinon on set emailExist à true
			emailExist = true;
			
			
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return emailExist;
	}
	
	public Boolean existEmail(String email) {
		Clients client = null;
		Boolean emailExist = false;
		
		try {
			Query query = getFactory().getEntityManager().createQuery("FROM Clients c WHERE c.email LIKE :email", Clients.class);
			query.setParameter("email", email);
			client =  (Clients) query.getSingleResult();
			
			// Si la requête ne renvoit rien on rentre dans le catch sinon on set emailExist à true
			emailExist = true;

			
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return emailExist;
	}
}
