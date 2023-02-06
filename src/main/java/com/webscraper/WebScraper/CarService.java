package com.webscraper.WebScraper;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//import org.jsoup.Jsoup;

public class CarService {
	
	// 1) jsoup variable.
	ArrayList<Car> _carList;
	
	public CarService() {
		// create object of jsoup variable.
		_carList = new ArrayList<Car>();
	}
	
	public void fetchData() {
		Document document = new Document("main");
		String parentUrl = "https://www.autotrader.ca/cars/on/?rcp=15&rcs=0&srt=35&prx=-2&prv=Ontario&loc=L7A%200J3&hprc=True&wcp=True&sts=New&adtype=Dealer&showcpo=1&inMarket=advancedSearch";
		document = this.getHtmlDocument(parentUrl);
		
		//Object[] href_links = document.select("//div[@class='listing-details']/h2/a").eachAttr("href").toArray();
		
		Object[] href_links = document.select("div.listing-details > h2 > a").eachAttr("href").toArray();
		
		for(int i = 0; i < href_links.length; i++) {
			Car car = new Car();
			System.out.println(href_links[i]);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Document detailsPage = new Document("detailsPage");
			detailsPage = this.getDetailsHtmlDocument("https://www.autotrader.ca"+href_links[i].toString(), parentUrl);
			
			//Elements el = detailsPage.select("p.hero-price");
			String price = detailsPage.select("p.hero-price").text();
			System.out.println(price);
			
			car.setPrice(price);
			_carList.add(car);
		}
		
		System.out.println(href_links.length);
	}
	
	private Document getHtmlDocument(String url) {
		Document doc = new Document("empty");
		
		try {
			doc = Jsoup.connect(url)
					.userAgent("Mozilla/5.0")
					.timeout(30000)
					.header("referer","https://www.autotrader.ca/")
					.cookie("atOptUser","9ef0393f-4acd-4cf3-b851-c2dcb830d386")
					.cookie()
					.cookie()
					.get();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return doc;
	}
	
	private Document getDetailsHtmlDocument(String url, String parentURL) {
		Document doc = new Document("empty2");
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(30000).header("referer",parentURL).get();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return doc;
	}
	
	

}


