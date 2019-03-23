package fr.amu.ter_genrest.tests.utils;


import java.time.LocalDateTime;
import java.time.Month;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class PayloadDataRequestREST {

	public static JsonObject jsonPayloadRequestUser() {
		return Json.createObjectBuilder().add("email", "leo@gmail.fr")
				.add("firstName", "leo")
				.add("lastName", "michel")
				.add("password", "leoa121")
				.build();
	}

	public static JsonObject jsonPayloadRequestProject(){
		LocalDateTime creationDate = LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0);
		LocalDateTime changeDate = LocalDateTime.of(2018, Month.DECEMBER, 26, 13, 37, 0);
		
		return  Json.createObjectBuilder().add("projectName", "firstProject").add("domaine", "domotique")
				.add("creationDate", creationDate.toString())
				.add("changeDate", changeDate.toString())
				.build();
	}

	public static JsonObject jsonPayloadRequestLanguage(){
		return  Json.createObjectBuilder().add("name", "JavaTest").build();
	}

	public static JsonObject jsonPayloadRequestConfiguration(){
		return  Json.createObjectBuilder()
				.add("name", "node-js-express")
				.add("pathFolder", "path")
				.add("description", "description")
				.build();
	}

	public static JsonObject jsonPayloadRequestOperatingSystem(){
		return Json.createObjectBuilder().add("name", "Ubuntu")
				.add("nameFolder", "ubuntu")
				.build();
	}

	public static JsonObject jsonPayloadRequestBuilding(){
		return Json.createObjectBuilder().add("type", "studio")
				.add("address", Json.createObjectBuilder().add("city", "Marseille").add("country", "France").add("street", "luminy").build())
				.build();
	}

	public static JsonObject jsonPayloadRequestFloor(){
		return  Json.createObjectBuilder().add("floorNumber", 5).build();
	}

	public static JsonObject jsonPayloadRequestCorridor(){
		return Json.createObjectBuilder().add("name", "666").build();
	}

	public static JsonObject jsonPayloadRequestZone(){
		return Json.createObjectBuilder().add("name", "9")
				.add("type", "kitchen")
				.build();
	}

	public static JsonObject jsonPayloadRequestRoom(){
		return Json.createObjectBuilder().add("type", "house")
				.add("name", "4")
				.build();
	}

	public static JsonObject jsonPayloadRequestActuator(){
		double longitude = 18.3;
		double lattitude = 20.0 ;
		return  Json.createObjectBuilder().add("latitude", lattitude)
				.add("longitude", longitude)
				.add("model", "AS12")
				.add("brand", "Brand20")
				.add("reference", "Ref00060")
				.add("state", "ON")
				.build();
	}   

	public static JsonObject jsonPayloadRequestSensor(){
		double longitude = 120.3;
		double lattitude = 95.0 ;
		return  Json.createObjectBuilder().add("latitude", lattitude)
				.add("longitude", longitude)
				.add("model", "Ezak")
				.add("brand", "Brand22")
				.add("reference", "Ref00065")
				.add("state", "OFF")
				.add("unitData", "Celsius")
				.build();
	}
}
