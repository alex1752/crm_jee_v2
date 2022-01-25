package CRM.Dao;

import java.util.List;

import CRM.model.Utilisateurs;

public interface UtilisateursDao {

	void ajouter(Utilisateurs utilisateur) throws DaoException;

    Utilisateurs trouver(long id) throws DaoException;

    List<Utilisateurs> lister() throws DaoException;

    void supprimer(long id) throws DaoException;

    void modifier(Utilisateurs utilisateur) throws DaoException;
}

