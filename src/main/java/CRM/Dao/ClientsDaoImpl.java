package CRM.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CRM.model.Clients;
import CRM.model.Commandes;


public class ClientsDaoImpl implements ClientsDao {

	private static final String SQL_INSERT = "INSERT INTO Clients (nom,prenom,entreprise,email, telephone,actif,notes) VALUES(?,?,?,?,?,?,?)";
	private static final String SQL_SELECT = "SELECT id,nom,prenom,entreprise,email,telephone,actif,notes FROM Clients";
	private static final String SQL_SELECT_BY_ID = "SELECT id,nom,prenom,entreprise,email,telephone,actif,notes FROM Clients WHERE id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM Clients WHERE id = ? ";
	private static final String SQL_SELECT_BY_NOM = "SELECT * FROM Clients WHERE nom LIKE CONCAT('%',?,'%')";
	
	private static final String SQL_UPDATE_BY_ID = "UPDATE Clients set nom = ?, prenom = ?, entreprise = ?, email = ?, telephone = ?, actif = ?, notes = ?  WHERE id = ?";
	private static final String SQL_SELECT_EMAIL_BY_ID = "SELECT email FROM Clients WHERE id = ?";
    private static final String SQL_SELECT_EMAIL = "SELECT email FROM Clients WHERE email like ?";


	private DaoFactory factory;

	public ClientsDaoImpl(DaoFactory factory) {
		this.factory = factory;
	}

	@Override
	public void ajouter(Clients client) throws DaoException {

		// Check si adresse email déjà présente
		Connection con = null;
		boolean emailExist = false;


		try {
            con = factory.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_SELECT_EMAIL_BY_ID);
            pst.setLong( 1, client.getId() );
            ResultSet rs  = pst.executeQuery();
        	if (!client.getEmail().equals(rs.getString( "email" ) ) ) {
                PreparedStatement pst2 = con.prepareStatement(SQL_SELECT_EMAIL);
                pst.setString( 1, client.getEmail() );
                ResultSet rs2  = pst.executeQuery();
                if ((rs.getString( "email" ).isEmpty())) {
                	emailExist = true;
            		throw new DaoException("Email d�j� existant");
                }
                rs2.close();
                pst2.close();
    		}
            rs.close();
            pst.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            factory.releaseConnection(con);
		}


		// Creation
		if (!emailExist) {
			try {

				con = factory.getConnection();

				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

				pst.setString(1, client.getNom());
				pst.setString(2, client.getPrenom());
				pst.setString(3, client.getEntreprise());
				pst.setString(4, client.getEmail());
				pst.setString(5, client.getTelephone());
				pst.setBoolean(6, client.isActif());
				pst.setString(7, client.getNotes());

				int statut = pst.executeUpdate();

				if (statut == 0) {
					throw new DaoException("Echec cr�ation Clients (aucun ajout)");
				}
				ResultSet rsKeys = pst.getGeneratedKeys();
				if (rsKeys.next()) {
					client.setId(rsKeys.getLong(1));
				} else {
					throw new DaoException("Echec cr�ation Clients (ID non retourn�)");
				}
				rsKeys.close();
				pst.close();

			} catch (SQLException ex) {
				throw new DaoException("Echec création Clients", ex);
			} finally {
				factory.releaseConnection(con);
			}
		}
	}

	@Override
	public Clients trouver(long id) throws DaoException {

		Clients client = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = factory.getConnection();
			pst = con.prepareStatement(SQL_SELECT_BY_ID);
			pst.setLong(1, id);

			rs = pst.executeQuery();
			if (rs.next()) {
				client = map(rs);
			}
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new DaoException("Erreur de recherche BDD Clients", ex);
		} finally {
			factory.releaseConnection(con);
		}
		return client;
	}

	@Override
	public List<Clients> lister() throws DaoException {

		List<Clients> listeClients = new ArrayList<>();
		Connection con = null;
		try {
			con = factory.getConnection();
			PreparedStatement pst = con.prepareStatement(SQL_SELECT);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				listeClients.add(map(rs));
			}
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new DaoException("Erreur de lecture BDD Clients", ex);
		} finally {
			factory.releaseConnection(con);
		}
		return listeClients;
	}

	@Override
	public List<Clients> listerParNom(String nom) throws DaoException {
		List<Clients> listeClients = new ArrayList<Clients>();
		Connection   con=null;
		try {
			  con = factory.getConnection();
			  PreparedStatement pst = con.prepareStatement( SQL_SELECT_BY_NOM );
			  pst.setString(1, nom);
		      ResultSet         rs  = pst.executeQuery();
		      while ( rs.next() ) {
	    	  	listeClients.add( map(rs) );
		      }
		      rs.close();
		      pst.close();
	    } catch(SQLException ex) {
	    	throw new DaoException("Erreur de lecture BDD Client", ex);
	    } finally {
	    	factory.releaseConnection(con);
		}
		return listeClients;
	}
	
	@Override
	public void supprimer(long id) throws DaoException {
		Connection con = null;

		CommandesDao commandesDao = DaoFactory.getInstance().getCommandesDao();

		List<Commandes> listeCommandes = commandesDao.trouverCommandesClient(id);
		for (Commandes c : listeCommandes) {
			commandesDao.supprimer(c.getId());
		}

		try {
			con = factory.getConnection();
			PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID);
			pst.setLong(1, id);
			int statut = pst.executeUpdate();
			if (statut == 0) {
				throw new DaoException("Erreur de suppression Clients(" + id + ")");
			}
			pst.close();
		} catch (SQLException ex) {
			throw new DaoException("Erreur de suppression BDD Clients", ex);
		} finally {
			factory.releaseConnection(con);
		}
	}



	@Override
	public void modifier(Clients client) throws DaoException {
		Connection con = null;
		boolean emailExist = false;


		try {
            con = factory.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_SELECT_EMAIL_BY_ID);
            pst.setLong( 1, client.getId() );
            ResultSet rs  = pst.executeQuery();
            rs.next();
        	if (!client.getEmail().equals(rs.getString( "email" ) ) ) {
                PreparedStatement pst2 = con.prepareStatement(SQL_SELECT_EMAIL);
                pst2.setString( 1, client.getEmail() );
                ResultSet rs2  = pst2.executeQuery();
                if ((rs2.getString( "email" ).isEmpty())) {
                	emailExist = true;
            		throw new DaoException("Email d�j� existant");
                }
                rs2.close();
                pst2.close();
    		}
            rs.close();
            pst.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            factory.releaseConnection(con);
		}





		if (!emailExist) {
			Long id = client.getId();
			try {
				con = factory.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_ID);

				pst.setString( 1, client.getNom() );
				pst.setString( 2, client.getPrenom() );
				pst.setString( 3, client.getEntreprise() );
				pst.setString( 4, client.getEmail() );
				pst.setString( 5, client.getTelephone() );
				pst.setBoolean( 6, client.isActif() );
				pst.setString( 7, client.getNotes() );
				pst.setLong( 8, client.getId() );


				int statut = pst.executeUpdate();
				if (statut == 0) {
					throw new DaoException("Erreur de modificaton Client(" + id + ")");
				}
				pst.close();
			} catch (SQLException ex) {
				throw new DaoException("Erreur de modification BDD Client", ex);
			} finally {
				factory.releaseConnection(con);
			}
		}
	}






	private static Clients map(ResultSet resultSet) throws SQLException {
		Clients c = new Clients();
		c.setId(resultSet.getLong("id"));
		c.setNom(resultSet.getString("nom"));
		c.setPrenom(resultSet.getString("prenom"));
		c.setEntreprise(resultSet.getString("entreprise"));
		c.setEmail(resultSet.getString("email"));
		c.setTelephone(resultSet.getString("telephone"));
		c.setActif(resultSet.getBoolean("Actif"));
		c.setNotes(resultSet.getString("notes"));
		return c;
	}

}
