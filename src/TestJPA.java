

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import persistence.UserProfile;

public class TestJPA {
	public static void main(String[] args){
		  EntityManagerFactory factory = Persistence.createEntityManagerFactory("javasoup");
		  EntityManager em = factory.createEntityManager();
		  UserProfile up = new UserProfile();
		  up.setCity("pune");
		  up.setCountry("india");
		  up.setDob(new Date());
		  up.setFutureSkill("data science");
		  up.setCurrentSkill("sap hana fiori");
		  em.getTransaction().begin();
		  em.persist(up);
		  em.getTransaction().commit();
		    
	}
}
