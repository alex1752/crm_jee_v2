package CRM.Dao;

import java.util.List;
import CRM.Dao.DaoException;
import CRM.model.Commandes;


public interface CommandesDao {

	void ajouter(Commandes commandes) throws DaoException;

    Commandes trouver(long id) throws DaoException;

    List<Commandes> lister() throws DaoException;

    void supprimer(long id) throws DaoException;

    void modifier(Commandes commandes) throws DaoException;

	List<Commandes> trouverCommandesClient(long id) throws DaoException;


}
