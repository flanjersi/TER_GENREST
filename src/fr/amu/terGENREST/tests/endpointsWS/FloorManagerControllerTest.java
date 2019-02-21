package fr.amu.terGENREST.tests.endpointsWS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
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

public class FloorManagerControllerTest {

	private static final String URL_ROOT_BUILDING = "http://localhost:8090/terGENREST/api/buildings/";
	private static final String URL_ROOT_FLOOR = "http://localhost:8090/terGENREST/api/floors/";

	private long idBuilding;

	@Before
	public void setup() throws IOException {
		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_BUILDING, PayloadDataRequestREST.jsonPayloadRequestBuilding());

		idBuilding = response.getPayload().getJsonNumber("id").longValue();
	}

	@After
	public void tearDown() throws IOException {
		RequestsHelper.httpDELETE(URL_ROOT_BUILDING + idBuilding);
	}


	@Test
	public void testCRUD() throws IOException {
		JsonObject jsonPayloadRequest = Json.createObjectBuilder()
				.add("floorNumber", 3)
				.build();

		RequestsHelper.ResponseJsonObject response = RequestsHelper.httpPUT(URL_ROOT_BUILDING + idBuilding + "/floors/", jsonPayloadRequest);

		assertEquals(201, response.getResponseCode());

		assertTrue(response.getPayload().containsKey("id"));
		assertFalse(response.getPayload().containsKey("floorNumber"));
		

		long idConfiguration = response.getPayload().getJsonNumber("id").longValue();
	}
}