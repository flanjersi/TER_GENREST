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
import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.user.UserManager;

@Path("api/users/{idUser:[0-9]+}/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectManagerControllerREST {
	
	
	@EJB
	private UserManager userManager;
	
	@EJB
	private ProjectManager projectManager;

	public ProjectManagerControllerREST() {

	}

	@GET
	@Path("")
	public Response getAllProject() {

		List<Project> languages = projectManager.findAllProject();
		
		return Response.ok().entity(languages).build();
	}
	
	@PUT
	@Path("")
	public Response createProject(@PathParam("idUser") Long idUser, Project project) {
		
		long id = 1L;
		
		if(userManager.findUser(id) == null) {
			User user1 = new  User("firstName", "lastName", "email007@email.com", "password");
			userManager.saveUser(user1);
			id = user1.getId();
		}
		
		User user = userManager.findUser(id);
		
		if( user == null){
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "User with id '" + idUser + "' not exist"))
					.build();
		}
					
		if(project.getProjectName() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'ProjectName' property is missing"))
					.build();
		}
		
		Long nbProjects = user.getProjects()
			.stream()
			.filter(projectUser -> (projectUser.getProjectName().equals(project.getProjectName())))
			.count();
		
		if(nbProjects != 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Project : '"+ project.getProjectName() +"' is already use"))
					.build();
		}
		
		user.addProject(project);
		userManager.updateUser(user);
		
		user = userManager.findUser(user.getId());
		
		Optional<Project> projectAdded = user.getProjects()
				.stream()
				.filter(projectUser -> (projectUser.getProjectName().equals(project.getProjectName())))
				.findFirst();
		
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", projectAdded.get().getId()).build();
			
		return Response.status(201).entity(jsonResponse).build();
	}
	
	
	
	@POST
	@Path("{id:[0-9]+}")
	public Response updateProject(@PathParam("id") Long id, Project project) {
		if(projectManager.findProject(id)== null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Language with id '" + id + "' no exist"))
					.build();
		}
		
		projectManager.updateProject(project);
		
		return Response.ok().entity(projectManager.findProject(id)).build();
	}

	@DELETE
	@Path("{id:[0-9]+}")
	public Response deleteProject(@PathParam("id") Long id) {
		if(projectManager.findProject(id) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Language with id '" + id + "' no exist"))
					.build();
		}
		
		projectManager.removeProject(projectManager.findProject(id));
		
		return Response.ok().build();
	}
	

}
