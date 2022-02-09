package CRM.Dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import CRM.model.Utilisateurs;

public class UtilisateursDao extends DaoObject<Utilisateurs>{

	public UtilisateursDao() {
		super(Utilisateurs.class);
		
	}

	public Utilisateurs find(String email) {
		Utilisateurs utilisateur = null;

		try {
			Query query = getFactory().getEntityManager().createQuery("FROM Utilisateur u WHERE u.email=:email", Utilisateurs.class);
			query.setParameter("email", email);
			
			utilisateur =  (Utilisateurs) query.getSingleResult();		
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return utilisateur;
	}
	
	public Utilisateurs find(String email, String motDePasse) {
		Utilisateurs utilisateur = null;

		try {
			Query query = getFactory().getEntityManager().createQuery("SELECT u FROM Utilisateur u WHERE u.email=:email AND u.motdepasse=:motDePasse", Utilisateurs.class);
			query.setParameter("email", email);
			query.setParameter("motDePasse", motDePasse);
			
			utilisateur = (Utilisateurs) query.getSingleResult();		
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return utilisateur;
	}
}

