package crawlers;

import java.io.IOException;
import java.text.ParseException;

public class MasterCrawler {
	public static void main(String[] args) throws IOException, InterruptedException, ParseException{
		crawl();
	}
	public static void crawl() throws IOException, InterruptedException, ParseException{
		UKGovCrawler.crawl("data science", 2);
	}
}
