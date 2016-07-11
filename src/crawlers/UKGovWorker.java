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

			Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
			
			// Insert into data lake first and then process it.
			
			DataLake dl = new DataLake();
			dl.setPayload(doc.toString().getBytes());
			
			em.getTransaction().begin();
			em.persist(dl);
			em.getTransaction().commit();

			
			
			Elements tds = doc.select("table.JSresults  tr  td");
			for (Element divElem : tds) {

				Elements div = divElem.select("div");

				Elements dtStr = div.select("div.mobileTd span");
				Elements jobIdElem = div.select("div.mobileTd a");

				long jobId = Long.parseLong(jobIdElem.get(0).attr("name"));
				if (em.find(UKGovJob.class, jobId) == null) {
					jobIdIsNotFound = true;
					
				
					UKGovJob govJob = new UKGovJob();
					govJob.setJobId(jobId);
					// posted on
					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

					// System.out.println(dtStr.get(0).text());

					Date postedOn = sdf.parse(dtStr.get(0).text());
					govJob.setPostedOn(postedOn);

					// posted by
					// System.out.println(dtStr.get(1).text());
					govJob.setPostedBy(dtStr.get(1).text());
					// location
					// System.out.println(dtStr.get(2).text());
					govJob.setLocation(dtStr.get(2).text());
					Elements aStr = div.select("div.mobileTd a");
					// position
					// System.out.println(aStr.get(1).text());
					govJob.setPosition(aStr.get(1).text());
					// job desc url
					// System.out.println(aStr.get(1).attr("href"));
					govJob.setLink(aStr.get(1).attr("href"));

					Random rand = new Random();

					Thread.sleep(rand.nextInt(10) + 6 * 1000);

					Document doc1 = Jsoup.connect(aStr.get(1).attr("href")).timeout(10 * 1000).get();
					Elements tds1 = doc1.select("div.jobDescription");

					for (Element tds1Elem : tds1) {
						// full job desc
						// take out all html tags
						// System.out.println(tds1Elem.html());
						govJob.setJobDescription(Jsoup.parse(tds1Elem.html()).text());

					}

					//Elements jdElem = doc1.select("div.jobViewSummary dl dd");
					Elements jtElem = doc1.select("div.jobViewSummary dl dt");
					for (Element jt : jtElem) {
						if (jt.text().equalsIgnoreCase("Job ID")) {

						} else if (jt.text().equalsIgnoreCase("Posting Date")) {

						} else if (jt.text().equalsIgnoreCase("Location")) {

						} else if (jt.text().equalsIgnoreCase("Industries")) {

							govJob.setIndustries(jt.nextElementSibling().text());
						} else if (jt.text().equalsIgnoreCase("Job type")) {
							govJob.setJobType(jt.nextElementSibling().text());
						} else if (jt.text().equalsIgnoreCase("Education Level")) {
							govJob.setEduLevel(jt.nextElementSibling().text());
						} else if (jt.text().equalsIgnoreCase("Salary")) {
							govJob.setSalary(jt.nextElementSibling().text());
						} else if (jt.text().equalsIgnoreCase("Job reference code")) {
							govJob.setJobRefCode(jt.nextElementSibling().text());
						}

					}

					em.getTransaction().begin();
					em.persist(govJob);
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
