package CRM.Dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import CRM.model.Utilisateurs;

public class UtilisateursDao extends DaoObject<Utilisateurs>{

	public UtilisateursDao() {
		super(Utilisateurs.class);
	}

	public Utilisateurs trouver(String email) {
		Utilisateurs utilisateur = null;

		try {
			Query query = getFactory().getEntityManager().createQuery("FROM Utilisateurs u WHERE u.email=:email", Utilisateurs.class);
			query.setParameter("email", email);
			
			utilisateur =  (Utilisateurs) query.getSingleResult();		
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			getFactory().releaseEntityManager();
		}
		
		return utilisateur;
	}
	
	public Utilisateurs trouver(String email, String motDePasse) {
		Utilisateurs utilisateur = null;

		try {
			Query query = getFactory().getEntityManager().createQuery("SELECT u FROM Utilisateurs u WHERE u.email=:email AND u.motDePasse=:motDePasse", Utilisateurs.class);
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

