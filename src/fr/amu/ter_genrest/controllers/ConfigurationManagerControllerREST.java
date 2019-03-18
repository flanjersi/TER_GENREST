package fr.amu.ter_genrest.controllers;

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

import fr.amu.ter_genrest.controllers.utils.Utils;
import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.services.environment_technical.ConfigurationManager;
import fr.amu.ter_genrest.services.environment_technical.LanguagesManager;
import fr.amu.ter_genrest.services.environment_technical.OperatingSystemManager;

@Path("api/configurations/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationManagerControllerREST {

	@EJB
	private LanguagesManager languagesManager;

	@EJB
	private ConfigurationManager configurationManager;
	
	@EJB
	private OperatingSystemManager operatingSystemManager;



	public ConfigurationManagerControllerREST() {}

	@GET
	@Path("{id:[0-9]+}")
	public Response getConfigurationById(@PathParam("id") Long id) {
		Configuration configuration = configurationManager.findById(id);

		if(configuration == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Configuration with id '" + id + "' not found"))
					.build();
		}

		return Response.ok().entity(configuration).build();
	}



	@POST
	@Path("{id:[0-9]+}")
	public Response updateConfiguration(@PathParam("id") Long id, Configuration configurationUpdate) {
		Configuration configuration = configurationManager.findById(id);

		if(configuration == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Configuration with id '" + id + "' not found"))
					.build();
		}

		if(configurationUpdate.getName() != null) {

			if(configurationManager.findByName(configurationUpdate.getName()) != null) {
				return Response
						.status(400)
						.entity(Utils.makeErrorMessage(400, "Configuration '" + configuration.getName() + "' already use"))
						.build();
			}

			configuration.setName(configurationUpdate.getName());
		}		

		if(configurationUpdate.getDescription() != null) {
			configuration.setDescription(configurationUpdate.getDescription());
		}

		if(configurationUpdate.getPathFolder() != null) {

			if(configurationManager.findByPathFolder(configurationUpdate.getPathFolder()) != null) {
				return Response
						.status(400)
						.entity(Utils.makeErrorMessage(400, "Path folder '" + configuration.getPathFolder() + "' already use"))
						.build();
			}

			configuration.setPathFolder(configurationUpdate.getPathFolder());
		}

		configuration = configurationManager.updateConfiguration(configuration);

		return Response.ok().entity(configuration).build();
	}
	
	@PUT
	@Path("{id:[0-9]+}/operatingsSystem")
	public Response createOperatingSystem(@PathParam("id") Long id, OperatingSystem operatingSystem) {

		Configuration configuration = configurationManager.findById(id);
		
		if(configuration == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No configuration with id '" + id + "'"))
					.build();					
		}
		
		
		if(operatingSystem.getName() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'name' property is missing"))
					.build();		
		}

		Optional<OperatingSystem> optionnal = configuration.getOperatingsSystem().stream()
												.filter(op -> op.getName().equals(operatingSystem.getName()))
												.findAny();
		
		if(optionnal.isPresent()) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "OperatingSystem '" + operatingSystem.getName() + "' already use"))
					.build();
		}

		if(operatingSystem.getNameFolder() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'nameFolder' property is missing"))
					.build();		
		}
		
		optionnal = configuration.getOperatingsSystem().stream()
				.filter(op -> op.getNameFolder().equals(operatingSystem.getNameFolder()))
				.findAny();


		if(optionnal.isPresent()) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "OperatingSystem with name folder '" + operatingSystem.getNameFolder() + "' already use"))
					.build();
		}

		configuration.getOperatingsSystem().add(operatingSystem);
		
		configuration = configurationManager.updateConfiguration(configuration);
		
		optionnal = configuration.getOperatingsSystem().stream()
				.filter(op -> op.getNameFolder().equals(operatingSystem.getNameFolder()))
				.findAny();

		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", optionnal.get().getId()).build();
		return Response.status(201).entity(jsonResponse).build();
	}
	
	@DELETE
	@Path("{id:[0-9]+}/operatingsSystem/{idOS:[0-9]+}")
	public Response deleteOperatingsystem(@PathParam("id") Long id, @PathParam("idOS") Long idOs) {
		Configuration configuration = configurationManager.findById(id);
		
		if(configuration == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No configuration with id '" + id + "'"))
					.build();					
		}
		
		Optional<OperatingSystem> optionnal = configuration.getOperatingsSystem().stream()
				.filter(op -> idOs.equals(op.getId()))
				.findAny();

		
		if(!optionnal.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "OperatingSystem with id '" + id + "' no exist"))
					.build();
		}
		
		configuration.getOperatingsSystem().remove(optionnal.get());
		
		configurationManager.updateConfiguration(configuration);

		return Response.ok().build();
	}
}
