package CRM.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import CRM.model.Clients;
import CRM.model.Commandes;


public class CommandesDao extends DaoObject<Commandes> {

	public CommandesDao() {
		super(Commandes.class);
	}

	public List<Commandes> listerParLabel(String label) {
		List<Commandes> objects = null;
		
		try {
			EntityManager em= getFactory().getEntityManager();
			Query query = em.createQuery("FROM "+ Commandes.class.getCanonicalName() +" WHERE label LIKE CONCAT('%',?1,'%')", Commandes.class);
			objects = query.setParameter(1, label).getResultList();
			
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return objects;
	}
	
	public List<Commandes> trouverCommandesClient(long idClient) {
		List<Commandes> objects = null;

		try {
			EntityManager em= getFactory().getEntityManager();
			objects = em.createQuery("FROM "+ Commandes.class.getCanonicalName() +" WHERE client_id = "+idClient,Commandes.class).getResultList();
			
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return objects;
	}
	
}
