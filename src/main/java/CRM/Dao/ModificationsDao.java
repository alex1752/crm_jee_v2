package CRM.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import CRM.model.Modification;

public class ModificationsDao extends DaoObject<Modification>{

	public ModificationsDao() {
		super(Modification.class);
		
	}
	
	public List<Modification> listerParUtilisateur(Long idUtilisateur) {
		List<Modification> objects = null;
	
		try {
			EntityManager em= getFactory().getEntityManager();
			objects = em.createQuery("FROM "+ Modification.class.getCanonicalName() +" WHERE id_utilisateur = " + idUtilisateur, Modification.class).getResultList();
		}finally {
			getFactory().releaseEntityManager();
		}
		
		return objects;
	}
}
