package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.user.UserManager;
import fr.amu.terGENREST.tests.utils.Utils;

public class ProjectManagerControllerRESTTest {

	
	@Test
	public void testCreateProject() throws IOException {
		
		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/user/100/projects");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("projectName", "MyfirstProject1").build();

		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = HttpClientBuilder.create().build().execute( request );

		assertEquals(201, response.getStatusLine().getStatusCode());

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("projectName"));


	}

}


