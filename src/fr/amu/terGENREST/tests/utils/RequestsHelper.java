package fr.amu.terGENREST.tests.utils;

import java.io.IOException;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Helper to test the RESTFul api
 * With that helper who can send GET, POST, PUT and DELETE HTTP request
 * @author Jeremy, med
 *
 */
public class RequestsHelper {

	private final static String ENCODING = "UTF-8"; 
	private final static String NAMEHEADER = "Content-Type";
	private final static String VALUEHEADER = "application/json";
	
	
	/**
	 * POJO to get the response of request with JsonObject response payload
	 * @author Jeremy
	 *
	 */
	public static class ResponseJsonObject {

		private long responseCode;

		private JsonObject payload;

		public ResponseJsonObject(long responseCode, JsonObject payload) {
			this.responseCode = responseCode;
			this.payload = payload;
		}

		public long getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(long responseCode) {
			this.responseCode = responseCode;
		}

		public JsonObject getPayload() {
			return payload;
		}

		public void setPayload(JsonObject payload) {
			this.payload = payload;
		}
	}

	/**
	 * POJO to get the response of request with JsonArray response payload
	 * @author Jeremy
	 *
	 */
	public static class ResponseJsonArray {

		private long responseCode;

		private JsonArray payload;

		public ResponseJsonArray(long responseCode, JsonArray payload) {
			this.responseCode = responseCode;
			this.payload = payload;
		}

		public long getResponseCode() {
			return responseCode;
		}

		public void setResponseCode(long responseCode) {
			this.responseCode = responseCode;
		}

		public JsonArray getPayload() {
			return payload;
		}

		public void setPayload(JsonArray payload) {
			this.payload = payload;
		}
	}

	public static ResponseJsonObject httpGetJsonObject(String url) throws IOException {
		HttpGet requestGetData = new HttpGet(url);

		HttpResponse response = HttpClientBuilder.create().build().execute( requestGetData );

		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		long responseCode = response.getStatusLine().getStatusCode();
		
		return new ResponseJsonObject(responseCode, responseObject);
	}

	public static ResponseJsonArray httpGetJsonArray(String url) throws IOException {
		HttpGet requestGetData = new HttpGet(url);

		HttpResponse response = HttpClientBuilder.create().build().execute( requestGetData );

		JsonArray responseArray = Utils.stringToJsonArray(EntityUtils.toString(response.getEntity()));

		long responseCode = response.getStatusLine().getStatusCode();
		
		return new ResponseJsonArray(responseCode, responseArray);
	}
	
	public static ResponseJsonObject httpPOST(String url, JsonObject payload) throws IOException {

		HttpPost requestUpdate = new HttpPost(url);

		requestUpdate.setEntity(new StringEntity(payload.toString(), ENCODING));
		requestUpdate.setHeader(NAMEHEADER, VALUEHEADER);

		HttpResponse response = HttpClientBuilder.create().build().execute( requestUpdate );


		long responseCode = response.getStatusLine().getStatusCode();
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		return new ResponseJsonObject(responseCode, responseObject);
	}

	public static ResponseJsonObject httpPUT(String url, JsonObject payload) throws IOException {
		HttpPut request = new HttpPut(url);

		request.setEntity(new StringEntity(payload.toString(), ENCODING));
		request.setHeader(NAMEHEADER, VALUEHEADER);

		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		
		JsonObject payloadResponse = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));

		long responseCode = response.getStatusLine().getStatusCode();

		return new ResponseJsonObject(responseCode, payloadResponse);
	}

	public static ResponseJsonObject httpDELETE(String url) throws IOException {

		HttpDelete requestDeleteData = new HttpDelete(url);
		HttpResponse response = HttpClientBuilder.create().build().execute( requestDeleteData );
		
		long responseCode = response.getStatusLine().getStatusCode();
				
		return new ResponseJsonObject(responseCode, null);
	}

}
