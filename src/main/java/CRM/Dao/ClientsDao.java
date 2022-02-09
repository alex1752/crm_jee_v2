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
	
//	  public boolean existEmail(String email) throws DaoException {
//	        boolean exist = false;
//	        Connection con=null;
//
//	        try {
//	            con = factory.getConnection();
//	            PreparedStatement pst;
//	            pst = con.prepareStatement(SQL_SELECT_EMAIL);
//	            pst.setString( 1, email );
//	            ResultSet rs  = pst.executeQuery();
//	            if (rs.next()) {
//	                if ((!rs.getString( "email" ).isEmpty())) {
//	                    exist = true;
//	                }
//	            }
//	            rs.close();
//	            pst.close();
//	        } catch(SQLException ex) {
//	            throw new DaoException("Erreur de recherche BDD Utilisateur", ex);
//	        } finally {
//	            factory.releaseConnection(con);
//	        }
//	        return exist;
//	    }
}
