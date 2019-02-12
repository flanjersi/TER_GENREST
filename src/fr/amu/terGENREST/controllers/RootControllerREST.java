package fr.amu.terGENREST.controllers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("api/")
public class RootControllerREST {
	
	@GET()
	@Path("")
	public String hello() {
		return "Root";
	}
}
