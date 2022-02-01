package CRM.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CRM.model.Utilisateurs;

public class UtilisateursDaoImpl implements UtilisateursDao {

	private static final String SQL_INSERT       = "INSERT INTO Utilisateurs (login, motDePasse, email) VALUES(?,?,?)";
    private static final String SQL_SELECT       = "SELECT id, login, motDepasse, email FROM Utilisateurs";
    private static final String SQL_SELECT_BY_ID = "SELECT id, login, motDepasse, email FROM Utilisateurs WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Utilisateurs WHERE id = ? ";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Utilisateurs set login = ?, motDePasse = ?, email = ?  WHERE id = ?";
    private static final String SQL_SELECT_EMAIL_BY_ID = "SELECT email FROM Utilisateurs WHERE id = ?";
    private static final String SQL_SELECT_EMAIL = "SELECT email FROM Utilisateurs WHERE email like ?";


	private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM Utilisateurs WHERE email LIKE ?";
	private static final String SQL_SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM Utilisateurs WHERE email LIKE ? AND motdepasse LIKE ?";


    private DaoFactory factory;

    public UtilisateursDaoImpl(DaoFactory factory) {
        this.factory = factory;
    }

	@Override
	public void ajouter(Utilisateurs utilisateur) throws DaoException {


		// V�rifier si l'email est pr�sent
		Connection con=null;
		boolean emailExist = false;




		try {
            con = factory.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_SELECT_EMAIL_BY_ID);
            pst.setLong( 1, utilisateur.getId() );
            ResultSet rs  = pst.executeQuery();
        	if (!utilisateur.getEmail().equals(rs.getString( "email" ) ) ) {
                PreparedStatement pst2 = con.prepareStatement(SQL_SELECT_EMAIL);
                pst2.setString( 1, utilisateur.getEmail() );
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
			try {

            con = factory.getConnection();

            PreparedStatement pst = con.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );

            pst.setString( 1, utilisateur.getLogin() );
            pst.setString( 2, utilisateur.getMotDePasse() );
            pst.setString( 3, utilisateur.getEmail() );


            int statut = pst.executeUpdate();

            if ( statut == 0 ) {
                throw new DaoException( "Echec cr�ation Utilisateur" );
            }
            ResultSet rsKeys = pst.getGeneratedKeys();
            if ( rsKeys.next() ) {
                utilisateur.setId( rsKeys.getLong( 1 ) );
            } else {
                throw new DaoException( "Echec cr�ation Utilisateur" );
        	}
            rsKeys.close();
            pst.close();

		    } catch(SQLException ex) {
		        throw new DaoException("Echec cr�ation Utilisateur",ex);
		    } finally {
		        factory.releaseConnection(con);
		    }
		}
	}


	@Override
	public Utilisateurs trouver (String email) throws DaoException {

		Utilisateurs            utilisateur=null;
        Connection        		con=null;
        PreparedStatement 		pst=null;
        ResultSet         		rs=null;

        try {
            con = factory.getConnection();
            pst = con.prepareStatement( SQL_SELECT_BY_EMAIL );
            pst.setString(1, email);
            rs  = pst.executeQuery();
            if ( rs.next() ) {
               utilisateur = map(rs);
            }
            rs.close();
            pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de recherche BDD Utilisateur", ex);
        } finally {
            factory.releaseConnection(con);
        }
        return utilisateur;
	}
	
	@Override
	public Utilisateurs trouver (String email, String motDePasse) throws DaoException {

		Utilisateurs            utilisateur=null;
        Connection        		con=null;
        PreparedStatement 		pst=null;
        ResultSet         		rs=null;

        try {
            con = factory.getConnection();
            pst = con.prepareStatement( SQL_SELECT_BY_EMAIL_AND_PASSWORD );
			pst.setString(1, email);
			pst.setString(2, motDePasse);
            rs  = pst.executeQuery();
            if ( rs.next() ) {
               utilisateur = map(rs);
            }
            
            rs.close();
            pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de recherche BDD Utilisateur", ex);
        } finally {
            factory.releaseConnection(con);
        }
        return utilisateur;
	}


	@Override
	public List<Utilisateurs> lister() throws DaoException {

		List<Utilisateurs> listeUtilisateurs = new ArrayList<>();
		Utilisateurs utilisateur;
        Connection   con=null;

        try {
              con = factory.getConnection();
              PreparedStatement pst = con.prepareStatement( SQL_SELECT );
              ResultSet         rs  = pst.executeQuery();
              while ( rs.next() ) {
            	  utilisateur = map(rs);
                  utilisateur.setMotDePasse( null );
                  listeUtilisateurs.add(utilisateur );
              }
              
              
              rs.close();
              pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de lecture BDD Utilisateurs", ex);
        } finally {
            factory.releaseConnection(con);
        }
        return listeUtilisateurs;
	}


	@Override
	public void supprimer(long id) throws DaoException {
		Connection   con=null;

        try {
              con = factory.getConnection();
              PreparedStatement pst = con.prepareStatement( SQL_DELETE_BY_ID );
              pst.setLong(1, id);
              int statut = pst.executeUpdate();
              if ( statut == 0 ) {
                  throw new DaoException("Erreur de suppression Utilisateurs("+id+")");
              }
              pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de suppression BDD Utilisateurs", ex);
        } finally {
            factory.releaseConnection(con);
        }

	}






	@Override
	public void modifier (Utilisateurs utilisateur) throws DaoException {

		 Connection con = null;
		 boolean emailExist = false;


			try {
	            con = factory.getConnection();
	            PreparedStatement pst = con.prepareStatement(SQL_SELECT_EMAIL_BY_ID);
	            pst.setLong( 1, utilisateur.getId() );
	            ResultSet rs  = pst.executeQuery();
	        	if (!utilisateur.getEmail().equals(rs.getString( "email" ) ) ) {
	                PreparedStatement pst2 = con.prepareStatement(SQL_SELECT_EMAIL);
	                pst2.setString( 1, utilisateur.getEmail() );
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
	    	   try {
			   	Long id = utilisateur.getId();
	            con = factory.getConnection();
	            PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_ID);


				pst.setString( 1, utilisateur.getLogin() );
				pst.setString( 2, utilisateur.getMotDePasse() );
				pst.setString( 3, utilisateur.getEmail() );
				pst.setLong( 4, utilisateur.getId() );

	            int statut = pst.executeUpdate();

	            if (statut == 0) {
	                throw new DaoException("Erreur de modificaton Utilisateurs(" + id + ")");
	            }
	            pst.close();
	        } catch (SQLException ex) {
	            throw new DaoException("Erreur de modification BDD Utilisateurs", ex);
	        } finally {
	            factory.releaseConnection(con);
	        }
		}
	}





	private static Utilisateurs map( ResultSet resultSet ) throws SQLException {
    	Utilisateurs u = new Utilisateurs();
        u.setId( resultSet.getLong( "id" ) );
        u.setLogin( resultSet.getString( "login" ) );
        u.setMotDePasse( resultSet.getString( "motDePasse" ) );
        u.setEmail( resultSet.getString( "email" ) );
        return u ;
	}
}