package CRM.Dao;

import java.util.List;

import CRM.model.Clients;

public interface ClientsDao {

	void ajouter(Clients client) throws DaoException;

    Clients trouver(long id) throws DaoException;
    
    boolean trouverId(long id) throws DaoException;

    List<Clients> lister() throws DaoException;

    void supprimer(long id) throws DaoException;

    void modifier(Clients client) throws DaoException;
}
