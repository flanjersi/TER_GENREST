package fr.amu.terGENREST.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LanguagesManagerControllerREST {

	@EJB
	private LanguagesManager languagesManager;
	
	public LanguagesManagerControllerREST() {
		
	}
	
	@GET
	@Path("")
	public Response getAllLanguages() {
		List<Language> languages = languagesManager.findAllLanguages();
		
		return Response.ok().entity(languages).build();
	}
	
	@GET
	@Path("{id:[0-9]+}")
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
	public Response createLanguage(Language language) {
		if(languagesManager.findByName(language.getName()) != null) {
			return Response
					.status(403)
					.entity(makeErrorMessage(403, "Language '" + language.getName() + "' already use"))
					.build();
		}
		
		//Delete the setup of the id by the user
		language.setId(0);
		
		languagesManager.addLanguage(language);
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", language.getId()).build();
		
		return Response.ok().entity(jsonResponse).build();
	}
	
	@POST
	@Path("{id:[0-9]+}")
	public Response updateLangage(@PathParam("id") Long id, Language language) {
		if(languagesManager.findById(id) == null) {
			return Response
					.status(403)
					.entity(makeErrorMessage(403, "Language with id '" + id + "' no exist"))
					.build();
		}
		
		if(id != language.getId()) {
			language.setId(id);
		}
		
		languagesManager.updateLanguage(language);
		
		return Response.ok().entity(languagesManager.findById(id)).build();
	}

	@DELETE
	@Path("{id:[0-9]+}")
	public Response deleteLanguage(@PathParam("id") Long id) {
		if(languagesManager.findById(id) == null) {
			return Response
					.status(403)
					.entity(makeErrorMessage(403, "Language with id '" + id + "' no exist"))
					.build();
		}
		
		languagesManager.removeLanguage(languagesManager.findById(id));
		
		return Response.ok().build();
	}
	
	private String makeErrorMessage(int code, String message) {
		return "{ error: " + message + ", code : " + code + "}";
	}
	
}
