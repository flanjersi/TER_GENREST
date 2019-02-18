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

import fr.amu.terGENREST.controllers.utils.Utils;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.user.UserManager;



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
	    
		if( userManager.findUserByEmail(user.getEmail()) != null ) {
			return Response
					.status(403)
					.entity(Utils.makeErrorMessage(403, "User '" + user.getEmail() + "' already use"))
					.build();
		}
		
		userManager.saveUser(user);
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", user.getId()).build();
		return Response.ok().entity(jsonResponse).build();
	}
	
	@POST
	@Path("/{id:[0-9]+}")
	public Response updateUser(@PathParam("id") Long id, User user) {
		if(userManager.findUser(id) == null) {
			return Response
					.status(403)
					.entity(Utils.makeErrorMessage(403, "User no exist"))
					.build();
		}
		
		if(id != user.getId()) {
			user.setId(id);
		}
		userManager.updateUser(user);
		return Response.ok().entity(userManager.findUser(id)).build();
	}
	
	
	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteUser(@PathParam("id") Long id) {
		if(userManager.findUser(id) == null) {
			return Response
					.status(403)
					.entity(Utils.makeErrorMessage(403, "User no exist"))
					.build();
		}
		
		userManager.removeUser(userManager.findUser(id));
		return Response.ok().build();
	}
	
	@GET
	@Path("/{email}/{password}")
	public Response getUserByEmailAndPassword(@PathParam("email") String email, @PathParam("password") String password) {	
		User user = userManager.authentification(email, password);
		if(user == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No user with : " + email+" "+ password))
					.build();
		}
		return Response.ok().entity(user).build();
	}

}
