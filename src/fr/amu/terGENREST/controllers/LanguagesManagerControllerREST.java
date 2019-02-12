package fr.amu.terGENREST.controllers;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.services.environmentTechnical.LanguagesManager;

@Path("api/languageManager")
public class LanguagesManagerControllerREST {

	@EJB
	private LanguagesManager languagesManager;
	
	public LanguagesManagerControllerREST() {
		
	}
	
	@GET
	@Path("{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLanguageById(@PathParam("id") Long id) {	
		Language language = languagesManager.findById(id);
		
		if(language == null) {
			return Response.noContent().build();
		}
		
		return Response.ok().entity(language).build();
	}
	
	
}
