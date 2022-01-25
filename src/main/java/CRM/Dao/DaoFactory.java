package CRM.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {

	
	private String url;
    private String username;
    private String passwd;
    private Connection con = null;

    private static DaoFactory instanceSingleton = null;

    // Constructeur privé
    private DaoFactory(String url, String username, String passwd) {
       this.url = url;
       this.username = username;
       this.passwd = passwd;
   }

   public static DaoFactory getInstance() {
       if ( DaoFactory.instanceSingleton == null ) {
           try {
                 Class.forName("org.postgresql.Driver");
                 DaoFactory.instanceSingleton = new DaoFactory("jdbc:postgresql://192.168.1.31/crm_1","maximiliano","");
         } catch(ClassNotFoundException e) {
             e.printStackTrace();
         }
       }
       return DaoFactory.instanceSingleton;
   }
   

   Connection getConnection() throws SQLException {
        if ( this.con == null ) {
        	this.con = DriverManager.getConnection(url,username,passwd);
        }
        return this.con;
    }
   
    public ClientsDao getClientsDao() {
    	return new ClientsDaoImpl (this);
    }
    public CommandesDao getCommandesDao() {
	    return new CommandesDaoImpl (this);
    }
    
    public UtilisateursDao getUtilisateurDao() {
	    return new UtilisateursDaoImpl (this);
    }
   
   // cette méthode prend une connection en parametre en présagent que l'on pourrait en utiliser plusieurs
   // mais par construction actuellement la seule connection existante est stockée dans "this.con"
   void releaseConnection( Connection connectionRendue ) {
       if (this.con==null) {
           return;
       }
       try {
           if ( ! this.con.isValid(10) ) {
               this.con.close();
               this.con = null;
           }
       } catch (SQLException e) {
           con = null;
       }
   }
}
