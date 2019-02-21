package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.Utils;


public class FloorManagerControllerRESTTest {

	private static final String URL_ROOT_BUILDING = "http://localhost:8090/terGENREST/api/buildings/";
	private static final String URL_ROOT_FLOOR = "http://localhost:8090/terGENREST/api/floors/";

	private long idBuilding;
	long idUser;
	long idProject;

	@Before
	public void setup() throws IOException {		
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/", PayloadDataRequestREST.jsonPayloadRequestUser());
		assertEquals(200, response.getResponseCode());
		idUser = response.getPayload().getJsonNumber("id").longValue();
		//ADD PROJECT
		response = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/users/" + idUser + "/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
		assertEquals(201, response.getResponseCode());
		idProject = response.getPayload().getJsonNumber("id").longValue();
		//ADD BUILDING 
		RequestsHelper.ResponseJsonObject response1 = RequestsHelper.httpPUT("http://localhost:8090/terGENREST/api/projects/"+ idProject + "/buildings", PayloadDataRequestREST.jsonPayloadRequestBuilding());
		assertEquals(201, response1.getResponseCode());
		idBuilding = response1.getPayload().getJsonNumber("id").longValue();
	}



	@Test
	public void testAddFloor() throws IOException {

		
		HttpPut requestProjectUpdate = new HttpPut("http://localhost:8090/terGENREST/api/buildings/" + idBuilding + "/floors");	
		JsonObject jsonPayloadRequest2 = Json.createObjectBuilder()
				.add("floorNumber", 513)
				.build();
		
		requestProjectUpdate.setEntity(new StringEntity(jsonPayloadRequest2.toString(), "UTF-8"));
		requestProjectUpdate.setHeader("Content-Type", "application/json");
		HttpResponse responseProject2 = HttpClientBuilder.create().build().execute( requestProjectUpdate );
		
		assertEquals(201, responseProject2.getStatusLine().getStatusCode());
		JsonObject responseObject2 = Utils.stringToJsonObject(EntityUtils.toString(responseProject2.getEntity()));
		assertTrue(responseObject2.containsKey("id"));

	}
}