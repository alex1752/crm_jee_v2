package CRM.Dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import CRM.Dao.DaoFactory;

public class DaoFactory {

	private static DaoFactory instanceSingleton = null;
	private static EntityManagerFactory entityManagerFactory = null;
	private EntityManager entityManager = null;
	
	private DaoFactory() {
		
	}
	
	public static DaoFactory getInstance() {
		if(DaoFactory.instanceSingleton == null) {
			DaoFactory.instanceSingleton  = new DaoFactory();
			DaoFactory.entityManagerFactory = Persistence.createEntityManagerFactory("CRMHibernate");
		}
		return DaoFactory.instanceSingleton;
	}

	public EntityManager getEntityManager() {
		if(this.entityManager == null || !this.entityManager.isOpen()) {
			this.entityManager = entityManagerFactory.createEntityManager();	
		}
		return this.entityManager;
	}

	public void releaseEntityManager() {
		if(this.entityManager != null && this.entityManager.isOpen()) {
			this.entityManager.close();
		}
	}
}
