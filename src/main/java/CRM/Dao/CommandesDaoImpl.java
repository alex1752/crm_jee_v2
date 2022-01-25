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

public class CommandesDaoImpl implements CommandesDao{
	
	
	private static final String SQL_INSERT       = "INSERT INTO Commandes (label,tjmHT,dureeJours,TVA, statut,typeCommande,notes,idClient) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT       = "SELECT id,label,tjmHT,dureeJours,TVA, statut,typeCommande,notes,idClient FROM Commandes";
    private static final String SQL_SELECT_BY_ID = "id,label,tjmHT,dureeJours,TVA, statut,typeCommande,notes,idClient FROM Commandes WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Commandes WHERE id = ? ";

    private static final String SQL_UPDATE_BY_ID = "UPDATE Commandes set label = ?, tjmHT = ?, dureeJours = ?, TVA = ?, statut = ?, typeCommande = ?, notes = ?, idClient = ? WHERE id = ?";

    private static final String SQL_SELECT_BY_IDCLIENT = "SELECT id,label,tjmHT,dureeJours,TVA, statut,typeCommande,notes,idClient FROM Commandes WHERE idclient = ?";

    
    private DaoFactory factory;

    public CommandesDaoImpl(DaoFactory factory) {
        this.factory = factory;
    }

	@Override
	public void ajouter(Commandes commande) throws DaoException {
		Connection con=null;
        try {
            con = factory.getConnection();

            PreparedStatement pst = con.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );

            pst.setString( 1, commande.getLabel() );
            pst.setFloat( 2, commande.getTjmHT() );
            pst.setFloat( 3, commande.getDureeJours() );
            pst.setFloat( 4, commande.getTVA() );
            pst.setString( 5, commande.getStatut() );
            pst.setString( 6, commande.getTypeCommande() );
            pst.setString( 7, commande.getNotes() );
            pst.setLong( 8, commande.getClient().getId() );
            
            int statut = pst.executeUpdate();

            if ( statut == 0 ) {
                throw new DaoException( "Echec création Commandes (aucun ajout)" );
            }
            ResultSet rsKeys = pst.getGeneratedKeys();

            if ( rsKeys.next() ) {

                commande.setId( rsKeys.getLong( 1 ) );
                

            } else {
                throw new DaoException( "Echec Création Commandes (ID non retourné)" );
            }
            rsKeys.close();
            pst.close();

        } catch(SQLException ex) {
            throw new DaoException("Echec cr�ation Clients",ex);
        } finally {
            factory.releaseConnection(con);
        }
	}

	@Override
	public Commandes trouver(long id) throws DaoException {

		Commandes         commande=null;
        Connection        con=null;
        PreparedStatement pst=null;
        ResultSet         rs=null;
        try {
        	

        	
        	
            con = factory.getConnection();
            pst = con.prepareStatement( SQL_SELECT_BY_ID );
            pst.setLong(1, id);
            rs  = pst.executeQuery();
            if ( rs.next() ) {
                commande = map(rs);
            }
            rs.close();
            pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de recherche BDD Commandes", ex);
        } finally {
            factory.releaseConnection(con);
        }
        return commande;
		
	}

	@Override
	public List<Commandes> trouverCommandesClient(long idclient) throws DaoException {
		
		
		List<Commandes> listeCommandes = new ArrayList<Commandes>();

        Connection        con=null;
        PreparedStatement pst=null;
        ResultSet         rs=null;

        try {

            con = factory.getConnection();
            pst = con.prepareStatement( SQL_SELECT_BY_IDCLIENT );
            pst.setLong(1, idclient);

            rs  = pst.executeQuery();

            while ( rs.next() ) {
            	listeCommandes.add(map(rs));
            }
            rs.close();
            pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de recherche BDD Commandes par idclient", ex);
        } finally {
            factory.releaseConnection(con);
        }
        return listeCommandes;
		
	}
	
	@Override
	public List<Commandes> lister() throws DaoException {
		
		List<Commandes> listeCommandes = new ArrayList<Commandes>();
        Connection   con=null;
        try {
              con = factory.getConnection();
              PreparedStatement pst = con.prepareStatement( SQL_SELECT );
              ResultSet         rs  = pst.executeQuery();
              while ( rs.next() ) {
                  listeCommandes.add( map(rs) );
              }
              rs.close();
              pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de lecture BDD Commandes", ex);
        } finally {
            factory.releaseConnection(con);
        }
        return listeCommandes;
		
	}

	@Override
	public void supprimer(long id) throws DaoException {
		
		Connection   con=null;
		
        try {
              con = factory.getConnection();
              PreparedStatement pst = con.prepareStatement( SQL_DELETE_BY_ID );
              pst.setLong(1, id);
              System.out.println(pst);
              int statut = pst.executeUpdate();
              if ( statut == 0 ) {
                  throw new DaoException("Erreur de suppression Commandes("+id+")");
              }
              pst.close();
        } catch(SQLException ex) {
            throw new DaoException("Erreur de suppression BDD Commandes", ex);
        } finally {
            factory.releaseConnection(con);
        }
	}

	@Override
	public void modifier(Commandes commandes) throws DaoException {
		
	       
        Connection con = null;
        Long id = commandes.getId();
        
        try {
            con = factory.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_ID);
            
                        
           
			pst.setString( 1, commandes.getLabel() );
			pst.setFloat( 2, commandes.getTjmHT() );
			pst.setFloat( 3, commandes.getDureeJours() );
			pst.setFloat( 4, commandes.getTVA() );
			pst.setString( 5, commandes.getStatut());
			pst.setString( 6, commandes.getTypeCommande() );
			pst.setString( 7, commandes.getNotes() );
			pst.setLong( 8, commandes.getClient().getId() );
			pst.setLong( 9, commandes.getId() );
           
  
            
            int statut = pst.executeUpdate();
            if (statut == 0) {
                throw new DaoException("Erreur de modificaton Commandes(" + id + ")");
            }
            pst.close();
        } catch (SQLException ex) {
            throw new DaoException("Erreur de modification BDD Commandes", ex);
        } finally {
            factory.releaseConnection(con);
        }
		
		
	}

	
	
	
	
	
	
	
	
	private static Commandes map( ResultSet resultSet ) throws SQLException {
    	Commandes c = new Commandes();
        c.setId( resultSet.getLong( "id" ) );
        c.setLabel( resultSet.getString( "label" ) );
        c.setTjmHT( resultSet.getFloat( "tjmHT" ) );
        c.setDureeJours( resultSet.getFloat( "dureeJours" ) );
        c.setTVA( resultSet.getFloat( "TVA" ) );
        c.setStatut( resultSet.getString ( "statut" )) ;
        c.setTypeCommande( resultSet.getString ( "typeCommande" )) ;
        c.setNotes( resultSet.getString( "notes" ) );
        
        ClientsDao clientDao = DaoFactory.getInstance().getClientsDao();
        try {
            c.setClient(clientDao.trouver(resultSet.getLong("idclient"))) ;
        } catch (DaoException e) {
        	e.printStackTrace();
        }
        
        
        return c;
	}
	
	
	
	
}
