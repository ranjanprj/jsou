package crawlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import persistence.DataLake;
import persistence.UKGovJob;
import utils.PersistenceFactory;

public class UKGovWorker {
	private static int pageNumber = 0;
	private static EntityManager em;
	private static boolean jobIdIsNotFound = true;

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		getUKGOV("data science", 1);
	}

	public static void getUKGOV(final String skillsName, final int maxPage)
			throws IOException, InterruptedException, ParseException {
		em = PersistenceFactory.getEM();
		int calMaxPage = maxPage < 0 ? 100 : maxPage;

		String escapedSkillsName = skillsName.replace(" ", "%20");

		do {
			pageNumber++;

			String url = String.format(
					"https://jobsearch.direct.gov.uk/JobSearch/PowerSearch.aspx?pp=25&pg=%d&sort=rv.dt.di&rad_units=miles&re=134&q=%s",
					pageNumber, escapedSkillsName);

			Document doc = Jsoup.connect(url).timeout(10 * 1000)
				     .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				      .referrer("http://www.google.com").get();
			
			// Insert into data lake first and then process it.
			
			DataLake dl = new DataLake();
			dl.setJobSiteType("UK_GOV_JOB");
			dl.setSearchTerm(skillsName);
			dl.setTargetUrl(url);
			
			dl.setPayload(doc.toString());
			
			em.getTransaction().begin();
			em.persist(dl);
			em.getTransaction().commit();

			// Get the document now from DataLake
			doc = Jsoup.parse(new String(dl.getPayload()));
			
			Elements tds = doc.select("table.JSresults  tr  td");
			for (Element divElem : tds) {

				Elements div = divElem.select("div");

//				Elements dtStr = div.select("div.mobileTd span");
				Elements jobIdElem = div.select("div.mobileTd a");

				long jobId = Long.parseLong(jobIdElem.get(0).attr("name"));
				if (em.find(UKGovJob.class, jobId) == null) {
					
					jobIdIsNotFound = true;					
					Elements aStr = div.select("div.mobileTd a");
				
					Random rand = new Random();

					Thread.sleep(rand.nextInt(10) + 6 * 1000);

					Document doc1 = Jsoup.connect(aStr.get(1).attr("href")).timeout(10 * 1000).get();
			
					DataLake subLinkDL = new DataLake();
					subLinkDL.setChildOfId(dl.getId());
					subLinkDL.setPayload(doc1.toString());
					subLinkDL.setJobSiteType("UK_GOV_JOB");
					subLinkDL.setSearchTerm(skillsName);
					subLinkDL.setTargetUrl(aStr.get(1).attr("href"));
					
					
					em.getTransaction().begin();
					em.persist(subLinkDL);
					em.getTransaction().commit();
	
				} else {
					// System.out.println("=========== JOB ID WAS FOUND
					// =========");
					jobIdIsNotFound = false;
				}
			}
			// System.out.println("=========== Jpage number =========");
			// System.out.println(pageNumber + " " + calMaxPage);
		} while (jobIdIsNotFound && pageNumber < calMaxPage);

	}

}
