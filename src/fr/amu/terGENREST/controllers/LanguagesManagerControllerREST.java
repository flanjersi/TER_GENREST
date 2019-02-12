package fr.amu.terGENREST.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.services.environmentTechnical.LanguagesManager;

@Path("api/language")
public class LanguagesManagerControllerREST {

	@EJB
	private LanguagesManager languagesManager;
	
	public LanguagesManagerControllerREST() {
		
	}
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLanguages() {
		List<Language> languages = languagesManager.findAllLanguages();
		
		return Response.ok().entity(languages).build();
	}
	
	@GET
	@Path("{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLanguageById(@PathParam("id") Long id) {	
		Language language = languagesManager.findById(id);
		
		if(language == null) {
			return Response
					.status(404)
					.entity(makeErrorMessage(404, "No language to id : " + id))
					.build();
		}
		
		return Response.ok().entity(language).build();
	}
	
	@PUT
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createLanguage(Language language) {
		return Response.noContent().build();
	}
	
	@POST
	@Path("")
	public Response updateLangage() {
		return Response.noContent().build();
	}
	
	private String makeErrorMessage(int code, String message) {
		return "{ error: " + message + ", code : " + code + "}";
	}
	
}
