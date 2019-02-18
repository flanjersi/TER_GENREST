package fr.amu.terGENREST.controllers;

import java.util.List;
import java.util.Optional;

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

import fr.amu.terGENREST.controllers.utils.Utils;
import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.services.environmentTechnical.ConfigurationManager;
import fr.amu.terGENREST.services.environmentTechnical.LanguagesManager;

@Path("api/language")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LanguagesManagerControllerREST {

	@EJB
	private LanguagesManager languagesManager;
	
	@EJB
	private ConfigurationManager configurationManager;

	
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
					.entity(Utils.makeErrorMessage(404, "No language with id : " + id))
					.build();
		}
		
		return Response.ok().entity(language).build();
	}
	
	@PUT
	@Path("")
	public Response createLanguage(Language language) {
		
		if(language.getName() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'name' property is missing"))
					.build();		
		}
		
		if(languagesManager.findByName(language.getName()) != null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Language '" + language.getName() + "' already use"))
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
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Language with id '" + id + "' no exist"))
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
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Language with id '" + id + "' no exist"))
					.build();
		}
		
		languagesManager.removeLanguage(languagesManager.findById(id));
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("{idLanguage:[0-9]+}/configurations")
	public Response createConfiguration(@PathParam("idLanguage") Long idLanguage, Configuration configuration) {
		Language language = languagesManager.findById(idLanguage);

		if(language == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Language with id '" + idLanguage + "' no exist"))
					.build();
		}

		if(configuration.getName() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'name' property is missing"))
					.build();			
		}
		
		if(configuration.getPathFolder() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'pathFolder' property is missing"))
					.build();			
		}
		
		
		if(configurationManager.findByName(configuration.getName()) != null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Configuration '" + configuration.getName() + "' already use"))
					.build();
		}
		if(configurationManager.findByPathFolder(configuration.getPathFolder()) != null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Path folder '" + configuration.getPathFolder() + "' already use"))
					.build();
		}


		//Delete the setup of the id by the user
		configuration.setId(0);

		language.addConfiguration(configuration);
		languagesManager.updateLanguage(language);

		
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", configurationManager.findByName(configuration.getName()).getId()).build();

		return Response.ok().entity(jsonResponse).build();
	}
	
	@DELETE
	@Path("{idLanguage:[0-9]+}/configurations/{id:[0-9]+}")
	public Response deleteConfiguration(@PathParam("idLanguage") Long idLanguage, @PathParam("id") Long id) {
		Language language = languagesManager.findById(idLanguage);

		if(language == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Language with id '" + idLanguage + "' no exist"))
					.build();
		}
		

		Optional<Configuration> configuration = language.getConfigurationsAvailable()
				.stream().filter(c -> c.getId() == id).findFirst();
		
		if(!configuration.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Configuration with id '" + id + "' not found"))
					.build();
		}
		
		language.removeConfiguration(configuration.get());
		languagesManager.updateLanguage(language);
		
		return Response.ok().build();
	}
	
}
