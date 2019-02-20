package fr.amu.terGENREST.controllers;

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
import fr.amu.terGENREST.entities.projectSpecifications.Building;
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

	@POST
	@Path("/{idProject:[0-9]+}")
	public Response updateProject(@PathParam("idProject") Long id, Project project) {

		Project projectFinded = projectManager.findProject(id);
		
		if(projectFinded == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Project with id '" + id + "' no exist"))
					.build();
		}

		if(project.getProjectName() != null) {
			projectFinded.setProjectName(project.getProjectName());
		}
		
		projectFinded = projectManager.updateProject(project);

		return Response.status(200).entity(projectFinded).build();
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

		//TODO Ajout v�rification pr�sence champs obligatoire pour l'ajout d'un batiment
		
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
				.stream().filter(c -> c.getId() == idBuilding).findFirst();

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
