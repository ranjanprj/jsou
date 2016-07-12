package crawlers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndeedCrawler {
	public static void main(String[] args) throws IOException, InterruptedException{
		
		Document doc1 = Jsoup.connect("http://www.indeed.co.in/jobs?q=Data+Science&l=Pune,+Maharashtra").timeout(10*1000).get();
		System.out.println(doc1);
		Elements tds1 = doc1.select("h2.jobtitle");
		System.out.println(tds1);
		
	
		
	}

}

