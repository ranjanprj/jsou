package javasoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JavaSoup {
	public static void main(String[] args) throws IOException, InterruptedException{
		Document doc = Jsoup.connect("https://jobsearch.direct.gov.uk/JobSearch/PowerSearch.aspx?pp=1&pg=1&where=london&sort=rv.dt.di&rad=20&rad_units=miles&re=134").timeout(10*1000).get();
		Elements tds = doc.select("table.JSresults  tr  td");
		
		for(int i=0;i<3;i++){

			 Elements div = tds.get(i).select("div");
			 
			 Elements dtStr = div.select("div.mobileTd span");
			 
			 System.out.println(dtStr.get(0).text());
			 System.out.println(dtStr.get(1).text());
			 System.out.println(dtStr.get(2).text());
			 
			 
			 Elements aStr = div.select("div.mobileTd a");			 
			 System.out.println(aStr.get(1).text());
			 System.out.println(aStr.get(1).attr("href"));
			 
			 Thread.sleep(3*1000);
			Document doc1 = Jsoup.connect(aStr.get(1).attr("href")).timeout(10*1000).get();
			Elements tds1 = doc1.select("div.jobDescription");
			System.out.println("==============================================================");
			for(Element tds1Elem : tds1){
				System.out.println(tds1Elem.html());
			}
			
//			tds1 = doc1.select("div.jobDescription p");
//						
//			for(Element tds1Elem : tds1){
//				System.out.println(tds1Elem.text());
//			}
			System.out.println("==============================================================");
			
			Elements jdElem = doc1.select("div.jobViewSummary dl dd");
			for(Element jdE : jdElem){
				System.out.println(jdE.text());	
			}
			
			System.out.println("==============================================================");
			
		}
		
	}

}

