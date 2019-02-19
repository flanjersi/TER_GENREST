package fr.amu.terGENREST.tests.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

public class JsonRequestandResponseManager {
	
	
	Map<HttpResponse,JsonObject> responseAndJsonObject = new HashMap<>();
	
	Map<HttpResponse,JsonArray> responseAndJsonArray = new HashMap<>();
	
	private final static String ENCODING = "UTF-8"; 
	private final static String NAMEHEADER = "Content-Type";
	private final static String VALUEHEADER = "application/json";
	
	public JsonRequestandResponseManager() {
	}
	
	// Put and Post Request have same body 
	public Map<HttpResponse,JsonObject> putRequest(String uri,JsonObject jsonObject) throws IOException {
		HttpPut request = new HttpPut(uri);
		
		// Use RequestUser prepared in Requesthelper
		JsonObject jsonPayloadRequest = jsonObject;
		
		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), ENCODING));
		request.setHeader(NAMEHEADER, VALUEHEADER);
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		System.out.println("laaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
		
		responseAndJsonObject.put(response, responseObject);
		return responseAndJsonObject;
	}
	
	public Map<HttpResponse,JsonObject> postRequest(String uri,JsonObject jsonObject) throws IOException {
		HttpPost request = new HttpPost(uri);
		
		// Use RequestUser prepared in Requesthelper
		JsonObject jsonPayloadRequest = jsonObject;
		
		request.setEntity(new StringEntity(jsonPayloadRequest.toString(), ENCODING));
		request.setHeader(NAMEHEADER, VALUEHEADER);
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		System.out.println("laaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
		
		responseAndJsonObject.put(response, responseObject);
		return responseAndJsonObject;
	}
	
	public Map<HttpResponse,JsonObject> getRequestJsonObject(String uri) throws IOException {
		HttpGet request = new HttpGet(uri);
		System.out.println(uri);
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		JsonObject responseObject = Utils.stringToJsonObject(EntityUtils.toString(response.getEntity()));
		
		responseAndJsonObject.put(response, responseObject);
		return responseAndJsonObject;
	}
	
	public Map<HttpResponse,JsonArray> getRequestJsonArray(String uri) throws IOException {
		HttpGet request = new HttpGet(uri);
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		JsonArray responseObject = Utils.stringToJsonArray(EntityUtils.toString(response.getEntity()));
		
		responseAndJsonArray.put(response, responseObject);
		return responseAndJsonArray;
	}

	public HttpResponse deleteRequest(String uri) throws IOException {
		HttpDelete request = new HttpDelete(uri);
		HttpResponse response  = HttpClientBuilder.create().build().execute( request );
		return response;
	}
	

}
