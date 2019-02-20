package fr.amu.terGENREST.controllers;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.terGENREST.controllers.utils.Utils;
import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.services.environmentTechnical.ConfigurationManager;
import fr.amu.terGENREST.services.environmentTechnical.LanguagesManager;

@Path("api/configurations/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationManagerControllerREST {

	@EJB
	private LanguagesManager languagesManager;

	@EJB
	private ConfigurationManager configurationManager;


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
}
