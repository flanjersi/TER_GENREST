package fr.amu.ter_genrest.controllers;

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
