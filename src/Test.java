import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.persistence.EntityManager;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import persistence.Comic;
import utils.PersistenceFactory;

public class Test {

	public static void main(String[] args) throws IOException {
		Response doc = Jsoup.connect("http://67.media.tumblr.com/5437a440819f35801d119f731c827855/tumblr_o9yzl8c29J1tlh6mdo1_1280.png").timeout(10 * 1000).ignoreContentType(true).execute();
		EntityManager em = PersistenceFactory.getEM();
		System.out.println(doc);
		Comic c = new Comic();
		c.setImage(doc.bodyAsBytes());
		
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		
		Comic cl = em.find(Comic.class, 251l);
		System.out.println(cl.getImage());
		
		File f = new File("D:\\output.png");
		
		FileOutputStream fis = new FileOutputStream(f);
		fis.write(cl.getImage());

	}

}
