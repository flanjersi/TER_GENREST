package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;


import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.Utils;

public class CorridorRESTControllerTest {
	
	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	private Long idUser;
	private Long idProject;
	private Long idBuilding;
	private Long idFloor;
	
	@Before
	public void setUp() throws Exception {
		//ADD USER
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_USER, PayloadDataRequestREST.jsonPayloadRequestUser());
		 idUser = response.getPayload().getJsonNumber("id").longValue();

		//ADD PROJECT
		response = RequestsHelper.httpPUT(URL_ROOT_USER + idUser + "/projects/", PayloadDataRequestREST.jsonPayloadRequestProject());
		 idProject = response.getPayload().getJsonNumber("id").longValue();

		//ADD BUILDING
		response = RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/", PayloadDataRequestREST.jsonPayloadRequestBuilding());
		 idBuilding = response.getPayload().getJsonNumber("id").longValue();
		 
		//ADD FLOOR
		response = RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floors/", 
				PayloadDataRequestREST.jsonPayloadRequestFloor());
		idFloor = response.getPayload().getJsonNumber("id").longValue();
	}
	
	@Test
	public void testCRUDUserRest() throws IOException {
		// add floor
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floor/"+idFloor+"/corridors/", 
												PayloadDataRequestREST.jsonPayloadRequestCorridor());
		Long idCorridor = response.getPayload().getJsonNumber("id").longValue();
		 
		// find floor by id
		RequestsHelper.ResponseJsonObject responseObject =
				RequestsHelper.httpGetJsonObject(URL_ROOT_PROJECT + idProject + "/buildings/"+ idBuilding+"/floor/"+idFloor+"/corridors/"+idCorridor);

		assertEquals(200, responseObject.getResponseCode()); 
		assertTrue(responseObject.getPayload().containsKey("numberCorridor"));
		assertEquals(6, responseObject.getPayload().getString("numberCorridors"));
	}
		
		// update corridor
//		HttpPost requestUpdate = new HttpPost("http://localhost:8090/terGENREST/api/users/" + id);
//
//		 jsonPayloadRequest = Json.createObjectBuilder().add("email", "jmj@gmail.dez")
//				.add("firstName", "JeanUpdate")
//				.add("lastName", "Marc")
//				.add("password", "zeoi")
//				.build();
//
//		requestUpdate.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//		requestUpdate.setHeader("Content-Type", "application/json");
//
//		response = HttpClientBuilder.create().build().execute( requestUpdate );
//
//		assertEquals(200, response.getStatusLine().getStatusCode());
//
//		responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
//
//		assertTrue(responseObject.containsKey("id"));
//		assertTrue(responseObject.containsKey("email"));
//		assertTrue(responseObject.containsKey("password"));
//		assertTrue(responseObject.containsKey("lastName"));
//		assertTrue(responseObject.containsKey("firstName"));
//
//		assertEquals("JeanUpdate", responseObject.getString("firstName"));
		
		


//		//Delete user
//
//		HttpDelete requestDeleteData = new HttpDelete("http://localhost:8090/terGENREST/api/users/" + id);
//		response = HttpClientBuilder.create().build().execute( requestDeleteData );
//		assertEquals(200, response.getStatusLine().getStatusCode());
		
	
}
