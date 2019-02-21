package fr.amu.terGENREST.tests.endpointsWS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.ejb.embeddable.EJBContainer;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;

import org.junit.Ignore;

import org.junit.Test;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.tests.utils.PayloadDataRequestREST;
import fr.amu.terGENREST.tests.utils.RequestsHelper;
import fr.amu.terGENREST.tests.utils.RequestsHelper.ResponseJsonObject;
import fr.amu.terGENREST.tests.utils.Utils;


public class BuildingRESTControllerTest {
	
	private static final String URL_ROOT_USER = "http://localhost:8090/terGENREST/api/users/";
	private static final String URL_ROOT_PROJECT = "http://localhost:8090/terGENREST/api/projects/";
	private static final String URL_ROOT_BUILDING = "http://localhost:8090/terGENREST/api/buildings/";

		//Add data
		private long idUser;
		private long idProject;
		private long idBuilding;
		RequestsHelper.ResponseJsonObject response;
		
		
//		@Before
		public void setup() throws IOException {
			
			//aDD user
			
			 response = RequestsHelper.httpPUT(URL_ROOT_USER, PayloadDataRequestREST.jsonPayloadRequestUser());
				idUser = response.getPayload().getJsonNumber("id").longValue();
				assertEquals(200, response.getResponseCode());
				
				
				//ADD Project
				
				response = RequestsHelper.httpPUT(URL_ROOT_USER+ idUser +"/projects", PayloadDataRequestREST.jsonPayloadRequestProject());
				idProject = response.getPayload().getJsonNumber("id").longValue();
				System.out.println("zzzzzzzzzzz"+idProject);
			
				assertEquals(201, response.getResponseCode());
			

		}
		@After
		public void tearDown() throws Exception {
			RequestsHelper.httpDELETE(URL_ROOT_USER + idUser);
		}



		@Test
		public void testAddBuilding() throws IOException {
//			HttpPut requestProject = new HttpPut(URL_ROOT_USER+ idUser +"/projects/"+ idProject +"/buildings/");
//			JsonObject jsonPayloadRequest = Json.createObjectBuilder().add("type", "Myqfqdsbat").build();
//			requestProject.setEntity(new StringEntity(jsonPayloadRequest.toString(), "UTF-8"));
//			requestProject.setHeader("Content-Type", "application/json");
//			HttpResponse responseProject = HttpClientBuilder.create().build().execute( requestProject );
//			assertEquals(201, responseProject.getStatusLine().getStatusCode());
//			JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(responseProject.getEntity()));
//			assertTrue(responseObject.containsKey("id"));
//			assertFalse(responseObject.containsKey("type"));
		
		}

//		
//		@Test
//		public void testRemoveProject() throws IOException {
//			HttpDelete requestDeleteProject = new HttpDelete("http://localhost:8090/terGENREST/api/users/"+ idUser + "/projects/"+ idProject+ "/buildings/"+idBuilding);
//			HttpResponse response2 = HttpClientBuilder.create().build().execute( requestDeleteProject );
//			assertEquals(200, response2.getStatusLine().getStatusCode());
//		}

}