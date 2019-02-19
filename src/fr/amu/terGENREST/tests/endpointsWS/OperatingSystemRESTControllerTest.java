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

public class OperatingSystemRESTControllerTest {

	@Test
	public void testCRUD() throws IOException {

		//Add data
		HttpPut request = new HttpPut("http://localhost:8090/terGENREST/api/operatingSystem/");

		
		JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("name", "Ubuntu").add("nameFolder", "ubuntu").build();

		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		request.setHeader("Content-Type", "application/json");

		HttpResponse response = HttpClientBuilder.create().build().execute( request );

		assertEquals(200, response.getStatusLine().getStatusCode());

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertFalse(responseObject.containsKey("name"));

		long id = responseObject.getJsonNumber("id").longValue();

		//Update data

		HttpPost requestUpdate = new HttpPost("http://localhost:8090/terGENREST/api/operatingSystem/" + id);

		jsonPayloadRequest = Json.createObjectBuilder()
				.add("name", "UbuntuUpdate")
				.add("nameFolder", "ubuntu")
				.build();

		requestUpdate.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
		requestUpdate.setHeader("Content-Type", "application/json");

		response = HttpClientBuilder.create().build().execute( requestUpdate );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("name"));
		assertTrue(responseObject.containsKey("nameFolder"));

		assertEquals("UbuntuUpdate", responseObject.getString("name"));
		assertEquals("ubuntu", responseObject.getString("nameFolder"));

		//Find data

		HttpGet requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/operatingSystem/" + id);

		response = HttpClientBuilder.create().build().execute( requestUpdate );

		assertEquals(200, response.getStatusLine().getStatusCode());

		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		assertTrue(responseObject.containsKey("id"));
		assertTrue(responseObject.containsKey("name"));
		assertTrue(responseObject.containsKey("nameFolder"));

		assertEquals("UbuntuUpdate", responseObject.getString("name"));
		assertEquals("ubuntu", responseObject.getString("nameFolder"));

		//Delete data

		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/operatingSystem/" + id);
		response = HttpClientBuilder.create().build().execute( requestDeleteData );
		assertEquals(200, response.getStatusLine().getStatusCode());

		//Find data deleted

		requestGetData = new HttpGet("http://localhost:8090/terGENREST/api/operatingSystem/" + id);

		response = HttpClientBuilder.create().build().execute( requestGetData );

		assertEquals(404, response.getStatusLine().getStatusCode());		
	}
	
}