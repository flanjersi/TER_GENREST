package fr.amu.terGENREST.controllers;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
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
import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.user.UserManager;

@Path("api/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectManagerControllerREST {
	
	
	@EJB
	private UserManager userManager;
	
	@EJB
	private ProjectManager projectManager;
	
	@EJB
	private BuildingManager buildingManager;
	
	
	public ProjectManagerControllerREST() {}

	@GET
	@Path("")
	public Response getAllProject(@PathParam("idUser") Long id) {	
		return Response.ok().entity(userManager.findUser(id).getProjects()).build();
	}
	
	@GET
	@Path("/{id:[0-9]+}")
	public Response getProjectById(@PathParam("id") Long id) {	
		Project project = projectManager.findProject(id);
		if(project == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No project with id : " + id))
					.build();
		}
		return Response.ok().entity(project).build();
	}
	
	@PUT
	@Path("")
	public Response createProject(@PathParam("idUser") Long id, Project project) {
		
		User user = userManager.findUser(id);
		
		if( user == null){
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "User with id '" + id + "' not exist"))
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
	@Path("")
	public Response updateProject(@PathParam("idUser") Long id, Project project) {
			
		if(userManager.findUser(id) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "User with id '" + id + "' no exist"))
					.build();
		}
		
		long nbProject = userManager.findUser(id).getProjects()
				.stream()
				.filter(projectUser -> (projectUser.getId().equals(project.getId())))
				.count();
		
		if(nbProject == 0) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "Project with id '" + project.getId() + "' no exist"))
					.build();
		}
	
		projectManager.updateProject(project);
		
		List<Project> projectsResearch = userManager.findUser(id).getProjects();		
		Optional<Project> projectResearch = projectsResearch.stream().filter(projectFinded -> projectFinded.equals(project)).findFirst();
				
		return Response.status(200).entity(projectResearch).build();
	}

	@DELETE
	@Path("/{idProject:[0-9]+}")
	public Response deleteProject(@PathParam("idProject") Long id,@PathParam("idUser") Long idUser) {
		
		Project projectRemoved = projectManager.findProject(id);
		
		if(projectRemoved == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Project with id '" + id + "' no exist"))
					.build();
		}
		
		User user = userManager.findUser(idUser);
		
		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User with id '" + idUser + "' no exist"))
					.build();
		}
		
		user.removeProject(projectRemoved);
		userManager.updateUser(user);
			
		return Response.ok().build();
	}
	
	
	@PUT
	@Path("{idProject:[0-9]+}/buildings")
	public Response createBuilding(@PathParam("idProject") Long idProject, Building building) {
		Project project = projectManager.findProject(idProject);
		if(project == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Project with id '" + idProject + "' no exist"))
					.build();
		}
		
		project.addBuilding(building);
		projectManager.updateProject(project);
		
		project = projectManager.findProject(project.getId());
		
		Optional<Building> buildingAdded = project.getBuilding()
				.stream()
				.filter(projectUser -> (projectUser.getType().equals(building.getType())))
				.findFirst();
		
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", buildingAdded.get().getId()).build();
			
		return Response.status(201).entity(jsonResponse).build();
			}
	@DELETE
	@Path("{idProject:[0-9]+}/buildings/{id:[0-9]+}")
	public Response deleteConfiguration(@PathParam("idProject") Long idProject, @PathParam("id") Long id) {
		Project project = projectManager.findProject(idProject);

		if(project == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "project id '" + idProject + "' no exist"))
					.build();
		}
		

		Optional<Building> building = project.getBuilding()
				.stream().filter(c -> c.getId() == id).findFirst();
		
		if(!building.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "building with id '" + id + "' not found"))
					.build();
		}
		
		project.removeBuildings(building.get());
		projectManager.updateProject(project);
		
		return Response.ok().build();
	}
	
	
}
