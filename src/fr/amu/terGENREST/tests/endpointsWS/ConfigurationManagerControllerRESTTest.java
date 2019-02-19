package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.Utils;

public class ConfigurationManagerControllerRESTTest {

	@Test
	public void testCRUD() throws IOException {

		//Add Language data
		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/language/");

		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("name", "JavaTest").build();

		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = HttpClientBuilder.create().build().execute( request );

		assertEquals(200, response.getStatusLine().getStatusCode());

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("name"));
		

		long id = responseObject.getJsonNumber("id").longValue();

		request = new HttpPut("http://localhost:8090/terGENREST/api/language/" + id + "/configurations/");

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();

		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		response = HttpClientBuilder.create().build().execute( request );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("name"));
		assertFalse(responseObject.containsKey("pathFolder"));
		assertFalse(responseObject.containsKey("description"));
		
		long idConfiguration = responseObject.getJsonNumber("id").longValue();

		//Update data

		HttpPost requestUpdate = new HttpPost("http://localhost:8090/terGENREST/api/configurations/" + idConfiguration);

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "node-js-expressUpdate")
				.add("pathFolder", "pathUpdate")
				.add("description", "descriptionUpdate")		
				.build();

		requestUpdate.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestUpdate.setHeader("Content-Type", "application/json");

		response = HttpClientBuilder.create().build().execute( requestUpdate );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("name"));
		assertTrue(responseObject.containsKey("pathFolder"));
		assertTrue(responseObject.containsKey("description"));

		assertEquals("node-js-expressUpdate", responseObject.getString("name"));
		assertEquals("pathUpdate", responseObject.getString("pathFolder"));
		assertEquals("descriptionUpdate", responseObject.getString("description"));

		
		//Find data

		HttpGet requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/configurations/" + idConfiguration);

		response = HttpClientBuilder.create().build().execute( requestGetData );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("name"));
		assertTrue(responseObject.containsKey("pathFolder"));
		assertTrue(responseObject.containsKey("description"));

		assertEquals("node-js-expressUpdate", responseObject.getString("name"));
		assertEquals("pathUpdate", responseObject.getString("pathFolder"));
		assertEquals("descriptionUpdate", responseObject.getString("description"));

		//Delete data

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/language/" + id + "/configurations/" + idConfiguration);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());

		//Find data deleted

		requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/configurations/" + idConfiguration);

		response = HttpClientBuilder.create().build().execute( requestGetData );

		assertEquals(404, response.getStatusLine().getStatusCode());		
	}
	
}