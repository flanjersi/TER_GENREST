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
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project.ProjectManager;
import fr.amu.ter_genrest.services.project_specifications.BuildingManager;
import fr.amu.ter_genrest.services.user.UserManager;

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
	@Path("{idProject:[0-9]+}/buildings")
	public Response createBuilding(@PathParam("idProject") Long idProject, Building building) {
		Project project = projectManager.findProject(idProject);
		
		if(project == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Project with id '" + idProject + "' no exist"))
					.build();
		}
		
		if(building.getType() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Building Type Required"))
					.build();
		}
		
		if(building.getAddress() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Building address required"))
					.build();			
		}
		
		if(building.getAddress().getCity() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Building address not completed : city is missing"))
					.build();			
		}
		
		if(building.getAddress().getCountry() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Building address not completed : country is missing"))
					.build();			
		}
		
		if(building.getAddress().getStreet() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "Building address not completed : street is missing"))
					.build();			
		}
	
		project.addBuilding(building);
		projectManager.updateProject(project);
		project = projectManager.findProject(project.getId());

		Optional<Building> buildingAdded = project.getBuilding()
				.stream()
				.max((b1, b2) -> Long.compare(b1.getId(), b2.getId()));

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", buildingAdded.get().getId()).build();
		return Response.status(201).entity(jsonResponse).build();
	}
	
	@DELETE
	@Path("{idProject:[0-9]+}/buildings/{idBuilding:[0-9]+}")
	public Response deleteBuilding(@PathParam("idProject") Long idProject, @PathParam("idBuilding") Long idBuilding) {
		Project project = projectManager.findProject(idProject);

		if(project == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "project with id '" + idProject + "' no exist"))
					.build();
		}

		Optional<Building> building = project.getBuilding()
				.stream()
				.filter(c -> c.getId().equals(idBuilding))
				.findFirst();

		if(!building.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "building with id '" + idBuilding + "' not found"))
					.build();
		}

		project.removeBuildings(building.get());
		projectManager.updateProject(project);

		return Response.ok().build();
	}
}
