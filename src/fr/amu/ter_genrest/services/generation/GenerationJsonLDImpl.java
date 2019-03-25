package fr.amu.ter_genrest.services.generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.apache.commons.io.FileUtils;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Actuator;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.Zone;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.project_specifications.Sensor;

@Stateless
public class GenerationJsonLDImpl implements GenerationJsonLD{

	@Override
	public void generateJsonLDDataFile(File fileDest, Project project) {

		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@context", generateContext());

		jsonbuilder.add("@id", "Project" + project.getId());

		jsonbuilder.add("@type", "sch:Project");

		jsonbuilder.add("sch:name", project.getProjectName());

		jsonbuilder.add("bot:hasBuilding", createJsonArrayBuildings(project.getBuilding()));

	
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileDest))) {
			writer.write(jsonbuilder.build().toString());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public JsonObject generateContext() {
		JsonObjectBuilder jsonContextObjectBuilder = Json.createObjectBuilder();


		jsonContextObjectBuilder.add("sch", "http://schema.org/");
		jsonContextObjectBuilder.add("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		jsonContextObjectBuilder.add("bot", "https://w3id.org/bot#");
		jsonContextObjectBuilder.add("ssn", "http://www.w3.org/ns/ssn/");
		jsonContextObjectBuilder.add("qu", "http://purl.org/NET/ssnx/qu/qu#");
		jsonContextObjectBuilder.add("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
		jsonContextObjectBuilder.add("dog", "http://elite.polito.it/ontologies/dogont.owl#");

		return jsonContextObjectBuilder.build();
	}

	private JsonArray createJsonArrayBuildings(Set<Building> buildings) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Building building : buildings) {
			jsonArrayBuilder.add(createJsonObjectBuilding(building));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectBuilding(Building building) {

		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Building");

		jsonbuilder.add("@id", "Building" + building.getId());

		jsonbuilder.add("rdfs:label", building.getType());

		jsonbuilder.add("sch:address", createJsonObjectAddress(building.getAddress()));

		jsonbuilder.add("bot:hasStorey", createJsonArrayStorey(building.getFloors()));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayStorey(Set<Floor> floors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Floor floor : floors) {
			jsonArrayBuilder.add(createJsonObjectStorey(floor));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectStorey(Floor floor) {

		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Storey");

		jsonbuilder.add("@id", "Storey" + floor.getId());

		jsonbuilder.add("rdfs:label", floor.getFloorNumber());

		jsonbuilder.add("bot:hasSpace", createJsonArrayCorridors(floor.getCorridors()));

		jsonbuilder.add("bot:hasZone", createJsonArrayZones(floor.getZones()));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayZones(Set<Zone> zones) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Zone motherRoom : zones) {
			jsonArrayBuilder.add(createJsonObjectZone(motherRoom));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectZone(Zone zone) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Zone");

		jsonbuilder.add("@id", "Zone" + zone.getId());

		jsonbuilder.add("rdfs:label", zone.getType() + ' ' + zone.getName());

		jsonbuilder.add("bot:hasSpace", mergeJsonArray(
				createJsonArrayCorridors(zone.getCorridors()),
				createJsonArrayRooms(zone.getRooms())
				)
				);

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayRooms(Set<Room> rooms) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Room room : rooms) {
			jsonArrayBuilder.add(createJsonObjectRoom(room));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectRoom(Room room) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Space");
		jsonbuilder.add("@type", "dog:Room");

		
		jsonbuilder.add("@id", "Room" + room.getId());

		jsonbuilder.add("rdfs:label", room.getType() + " " + room.getName());

		jsonbuilder.add("bot:hasElement", mergeJsonArray(
				createJsonArraySensors(room.getSensors()),
				createJsonArrayActuators(room.getActuators())
				)
				);

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayCorridors(Set<Corridor> corridors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Corridor corridor : corridors) {
			jsonArrayBuilder.add(createJsonObjectCorridor(corridor));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectCorridor(Corridor corridor) {		
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Space");

		jsonbuilder.add("@id", "Corridor" + corridor.getId());

		jsonbuilder.add("rdfs:label", corridor.getName());


		jsonbuilder.add("bot:hasElement", mergeJsonArray(
				createJsonArraySensors(corridor.getSensors()),
				createJsonArrayActuators(corridor.getActuators())
				)
				);

		return jsonbuilder.build();
	}

	private JsonObject createJsonObjectAddress(Address address) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "sch:PostalAddress");

		jsonbuilder.add("sch:addressCountry", address.getCountry());
		jsonbuilder.add("sch:addressLocality", address.getCity());
		jsonbuilder.add("sch:streetAddress", address.getStreet());

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayActuators(Set<Actuator> actuators) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Actuator actuator : actuators) {
			jsonArrayBuilder.add(createJsonObjectActuator(actuator));
		}

		return jsonArrayBuilder.build();	
	}

	private JsonValue createJsonObjectActuator(Actuator actuator) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "ssn:Actuator");

		jsonbuilder.add("@id", actuator.getName());

		
		
		jsonbuilder.add("geo:hasLocation", createLocation("Location" + actuator.getName(), actuator.getLatitude(), actuator.getLongitude()));

		jsonbuilder.add("rdfs:label", actuator.getModel());



		return jsonbuilder.build();
	}
	
	private JsonValue createLocation(String id, double latitude, double longitude) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "geo:Point");

		jsonbuilder.add("@id", id);

		
		jsonbuilder.add("geo:lat", latitude);
		jsonbuilder.add("geo:long", longitude);


		return jsonbuilder.build();
	}

	private JsonArray createJsonArraySensors(Set<Sensor> sensors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Sensor sensor : sensors) {
			jsonArrayBuilder.add(createJsonObjectSensor(sensor));
		}

		return jsonArrayBuilder.build();	}

	private JsonObject createJsonObjectSensor(Sensor sensor) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "ssn:Sensor");

		jsonbuilder.add("@id", sensor.getName());

		jsonbuilder.add("geo:hasLocation", createLocation("Location" + sensor.getName(), sensor.getLatitude(), sensor.getLongitude()));

		jsonbuilder.add("rdfs:label", sensor.getModel());
		jsonbuilder.add("qu:QuantityKind", sensor.getQuantityKind());
		jsonbuilder.add("qu:Unit", sensor.getUnitData());

		return jsonbuilder.build();
	}

	private JsonArray mergeJsonArray(JsonArray jsonArray1, JsonArray jsonArray2) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(JsonValue jsonValue : jsonArray1) {
			jsonArrayBuilder.add(jsonValue);
		}

		for(JsonValue jsonValue : jsonArray2) {
			jsonArrayBuilder.add(jsonValue);
		}

		return jsonArrayBuilder.build();

	}


}
