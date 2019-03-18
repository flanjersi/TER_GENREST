package fr.amu.ter_genrest.controllers;

import java.io.File;
import java.io.FileNotFoundException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.ter_genrest.controllers.utils.Utils;
import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.environment_technical.ConfigurationManager;
import fr.amu.ter_genrest.services.environment_technical.LanguagesManager;
import fr.amu.ter_genrest.services.environment_technical.OperatingSystemManager;
import fr.amu.ter_genrest.services.generation.GenerationJsonLD;
import fr.amu.ter_genrest.services.generation.WebServiceGenerator;
import fr.amu.ter_genrest.services.project.ProjectManager;
import fr.amu.ter_genrest.services.user.UserManager;
import fr.amu.ter_genrest.services.utils.DirectoryManager;
import fr.amu.ter_genrest.services.utils.SendMail;

@Path("api/deploiement")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WebServiceGeneratorREST {
	
	@EJB
	private UserManager userManager;
	
	@EJB
	private ProjectManager projectManager;
	
	@EJB
	private LanguagesManager languageManager;
	
	@EJB
	private ConfigurationManager configurationManager;
	
	@EJB
	private OperatingSystemManager operatingSystemManager;
	
	@EJB
	private WebServiceGenerator webServiceGenerator;
	
	@EJB
	private GenerationJsonLD generationJsonLD;
	
	@Context 
	ServletContext servletContext;

	@EJB
	DirectoryManager directoryManager;
	
	@EJB
	SendMail sendMail;
	
	@GET
	@Path("/") // http://localhost:8090/terGENREST/api/deploiement?project=5&language=2&configuration=1&operatingSystem=1
	public Response generateAPI( @QueryParam("project") Long idProject, @QueryParam("language") Long idLanguage,
			@QueryParam("configuration") Long idConfiguration, @QueryParam("operatingSystem") Long idOperatingSystem) {
		
		Project project = projectManager.findProject(idProject);
		if( project.equals(null) ) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No project has been found "))
					.build();
		}
		
		Language language = languageManager.findById(idLanguage);
		if(language.equals(null) ) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No language has been found "))
					.build();
		}
		
		Configuration configuration = configurationManager.findById(idConfiguration); 
		if(configuration.equals(null) ) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No configuration has been found"))
					.build();
		}
		
		OperatingSystem operatingSystem = operatingSystemManager.findById(idOperatingSystem);
		if(operatingSystem.equals(null) ) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No operatingSystem has been found"))
					.build();
		}

		String generatedDirectoryPath = webServiceGenerator.engine( project, language, configuration, operatingSystem);
		
		generationJsonLD.generateJsonLDDataFile(new File(generatedDirectoryPath + File.separator + "data.jsonld"), project);
		
		 // ZIP
		String generatedZipDirectoryPath = directoryManager.zipDirectory(generatedDirectoryPath);

		// DELETE
		try {
			// delete folder
			directoryManager.deleteFolder(generatedDirectoryPath);
			
			// delete zip
			// directoryManager.deleteFolder(generatedZipDirectoryPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return Response.ok().build();
	}
	
	
}
