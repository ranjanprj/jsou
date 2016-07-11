package processors;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawlertest.UKGov;
import persistence.DataLake;
import persistence.UKGovJob;
import utils.PersistenceFactory;

public class UKGovProcessor {
	private static EntityManager em;

	public static void Processor() throws IOException, InterruptedException, ParseException {

		em = PersistenceFactory.getEM();

		List<DataLake> dl = em
				.createQuery("SELECT t FROM DataLake t WHERE  t.childOfId = :id and t.jobSiteType = 'UK_GOV_JOB'",
						DataLake.class)
				.setParameter("id", 0).getResultList();
		
		
		List<UKGovJob> govJobs = new ArrayList<UKGovJob>();
		for (DataLake d : dl) {		

			Document doc = Jsoup.parse(d.getPayload());
			Elements tds = doc.select("table.JSresults tr td");

			for (Element divElem : tds) {
				UKGovJob govJob = new UKGovJob();
				Elements div = divElem.select("div");

				Elements dtStr = div.select("div.mobileTd span");
				Elements jobIdElem = div.select("div.mobileTd a");

				long jobId = Long.parseLong(jobIdElem.get(0).attr("name"));

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

				// Random rand = new Random();
				// Thread.sleep(rand.nextInt(10) + 6 * 1000);

				govJobs.add(govJob);
			}
			System.out.println(govJobs.size());
			// ======================================================================================
			// Fetch all the sub documents
			List<DataLake> dls = em.createQuery("SELECT t from DataLake t where t.childOfId = :id", DataLake.class)
					.setParameter("id", d.getId()).getResultList();
			System.out.println(dls.size());
			for (int i=0; i<dls.size(); i++) {
				UKGovJob govJob = govJobs.get(i);
				DataLake dataLake = dls.get(i);
				Document doc1 = Jsoup.parse(dataLake.getPayload());

				Elements jobTitle = doc1.select("div.jobViewContent h2");

				try {
					if (jobTitle.get(0) != null) {
						govJob.setPostedBy(jobTitle.get(0).text());
					}
					if (jobTitle.get(1) != null) {
						govJob.setPosition(jobTitle.get(1).text());
					}

				} catch (Exception ex) {

				}

				govJob.setLink(dataLake.getTargetUrl());

				Elements tds1 = doc1.select("div.jobDescription");
				for (Element tds1Elem : tds1) {
					// full job desc
					// take out all html tags
					// System.out.println(tds1Elem.html());
					govJob.setJobDescription(Jsoup.parse(tds1Elem.html()).text());
					System.out.println(govJob.getJobDescription());

				}
				// Elements jdElem = doc1.select("div.jobViewSummary dl
				// dd");
				Elements jtElem = doc1.select("div.jobViewSummary dl dt");
				for (Element jt : jtElem) {
					if (jt.text().equalsIgnoreCase("Job ID")) {
//						govJob.setJobId(Long.parseLong(jt.nextElementSibling().text()));
					} else if (jt.text().equalsIgnoreCase("Posting Date")) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

						Date postedOn = sdf.parse(jt.nextElementSibling().text());
//						govJob.setPostedOn(postedOn);
					} else if (jt.text().equalsIgnoreCase("Location")) {
//						govJob.setLocation(jt.nextElementSibling().text());

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
			}

		}

	}
}