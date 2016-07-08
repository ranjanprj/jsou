package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceFactory {

	public static EntityManager getEM(){
		  EntityManagerFactory factory = Persistence.createEntityManagerFactory("javasoup");
		  EntityManager em = factory.createEntityManager();
		  return em;
	}
}
