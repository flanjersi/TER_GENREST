package fr.amu.terGENREST.tests.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Utils {

	public static String readContentOfStream(InputStream inputStream) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder strBuilder = new StringBuilder();

		String output;
		while ((output = br.readLine()) != null) {
			strBuilder.append(output);
		}
		
		return strBuilder.toString();
	}
	
	public static JsonObject stringToJsonObject(String strJson) {
		JsonReader jsonReader = Json.createReader(new StringReader(strJson));
		
		JsonObject jsonObject = jsonReader.readObject();
		
		jsonReader.close();
		
		return jsonObject;
	}
	
	public static JsonArray stringToJsonArray(String strJson) {
		JsonReader jsonReader = Json.createReader(new StringReader(strJson));
		
		JsonArray jsonArray = jsonReader.readArray();
		
		jsonReader.close();
		
		return jsonArray;
	}
}
