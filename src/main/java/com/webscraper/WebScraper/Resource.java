package com.webscraper.WebScraper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/cars")
public class Resource {
	
	@GET
	@Path("/getall")
	public void getAll() {
		CarService carService = new CarService();
		carService.fetchData();
	}
	
		
	

}
