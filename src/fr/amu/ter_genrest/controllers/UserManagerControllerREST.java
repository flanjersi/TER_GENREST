package fr.amu.ter_genrest.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser.Event;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.amu.ter_genrest.controllers.utils.Utils;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.user.UserManager;



@Path("api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserManagerControllerREST {

	@EJB
	private UserManager userManager;

	public UserManagerControllerREST(){
	}

	@GET
	public Response getAllUsers() {
		List<User> users = userManager.findAllUser();
		return Response.ok().entity(users).build();
	}

	@GET
	@Path("/{id:[0-9]+}")
	public Response getUserById(@PathParam("id") Long id) {	
		
		User user = userManager.findUser(id);
		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No User to id : " + id))
					.build();
		}
		return Response.ok().entity(user).build();
	}

	@PUT
	public Response createUser(User user) {
		
		if( user.getEmail() == null ) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, " 'email' property is missing"))
					.build();
		}

		if( user.getFirstName() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, " 'firstname' property is missing"))
					.build();
		}
		
		if( user.getLastName() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, " 'lastname' property is missing"))
					.build();
		}

		if( user.getPassword() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, " 'password' property is missing"))
					.build();
		}

		if(userManager.findUserByEmail(user.getEmail()) != null ) {
			return Response
					.status(403)
					.entity(Utils.makeErrorMessage(403, "User '" + user.getEmail() + "' already used"))
					.build();
		}

		userManager.saveUser(user);
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", user.getId()).build();
		return Response.ok().entity(jsonResponse).build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateUser(@PathParam("id") Long id, User user) {
	
		User findedUser = userManager.findUser(id);
		if( findedUser == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User with id '" + id + "' not found"))
					.build();
		}

		if( user.getEmail() != null) {
			if(!user.getEmail().equals(findedUser.getEmail()) )
			{	if( userManager.findUserByEmail(user.getEmail()) == null  ) {
				findedUser.setEmail(user.getEmail());
			}else {
				return Response
						.status(403)
						.entity(Utils.makeErrorMessage(403, "User '" + user.getEmail() + "' already used"))
						.build();
			}
			}
		}
		if( user.getFirstName() != null) {
			findedUser.setFirstName(user.getFirstName());
		}
		if( user.getLastName() != null) {
			findedUser.setLastName(user.getLastName());
		}

		if( user.getPassword() != null) {
			findedUser.setPassword(user.getPassword());
		}

		userManager.updateUser(findedUser);
		return Response.ok().entity(userManager.findUser(id)).build();
	}

	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteUser(@PathParam("id") Long id) {
		if(userManager.findUser(id) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User with id '" + id +"' not found"))
					.build();
		}
		userManager.removeUser(userManager.findUser(id));
		return Response.ok().build();
	}

	@GET
	@Path("/authentification")
	public Response getUserByEmailAndPassword(@QueryParam("email") String email, @QueryParam("password") String password) {			

		User user = userManager.authentification(email, password);
		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No user with email : " + email + " and password : " + password))
					.build();
		}
		return Response.ok().entity(user).build();
	}
	
	@GET
	@Path("/query")
	public Response getUserByEmailAndPassword(@QueryParam("email") String email) {			

		User user = userManager.findUserByEmail(email);
		
		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No user with email : " + email))
					.build();
		}
		return Response.ok().entity(user).build();
	}

	@GET
	@Path("/{id:[0-9]+}/projects")
	public Response getAllProject(@PathParam("id") Long id) {
		User user = userManager.findUser(id);

		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User with id '" + id + "' not found"))
					.build();
		}
		return Response.ok().entity(user.getProjects()).build();
	}

	@PUT
	@Path("/{idUser:[0-9]+}/projects")
	public Response createProject(@PathParam("idUser") Long id, Project project) {

		
		User user = userManager.findUser(id);
		if(user == null){
			return  Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User with id '" + id + "' not exist"))
					.build();
		}
		
		Long projectExiste = user.getProjects()
				.stream()
				.filter(projectUser -> projectUser.getProjectName().equals(project.getProjectName()))
				.count();

		if(projectExiste != 0) {
			return  Response
					.status(403)
					.entity(Utils.makeErrorMessage(403, "'Project already use, please change the project name"))
					.build();
		}

		if(project.getProjectName() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'ProjectName' property is missing"))
					.build();
		}

		if(project.getDomaine() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Project domain' property is missing"))
					.build();
		}
				
		if( project.getCreationDate() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Creation date' property is missing"))
					.build();
		}
		
		Long nbProjects = user.getProjects()
				.stream()
				.filter(projectUser -> (projectUser.getProjectName().equals(project.getProjectName())))
				.count();

		if(nbProjects != 0) {
			return  Response
					.status(403)
					.entity(Utils.makeErrorMessage(403, "Project : '"+ project.getProjectName() +"' is already use"))
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


	@DELETE
	@Path("/{idUser:[0-9]+}/projects/{idProject:[0-9]+}")
	public Response deleteProject(@PathParam("idUser") Long idUser, @PathParam("idProject") Long idProject) {

		User user = userManager.findUser(idUser);

		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User with id '" + idUser + "' no exist"))
					.build();
		}

		Optional<Project> projectRemoved = user.getProjects()
				.stream()
				.filter(projectUser -> projectUser.getId().equals(idProject))
				.findFirst();

		if(!projectRemoved.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "User don't have project with id '" + idProject + "'"))
					.build();
		}

		user.removeProject(projectRemoved.get());
		userManager.updateUser(user);

		return Response.ok().build();
	}
}