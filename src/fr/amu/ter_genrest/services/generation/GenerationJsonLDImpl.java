package fr.amu.ter_genrest.services.generation;

import java.util.List;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Actuator;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.project_specifications.Sensor;

@Stateless
public class GenerationJsonLDImpl implements GenerationJsonLD{

	@Override
	public JsonObject generateJsonLDDataFile(Project project) {

		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();
		
		jsonbuilder.add("@context", generateContext());
		
		jsonbuilder.add("@id", "Project" + project.getId());
		
		jsonbuilder.add("@type", "sch:Project");
		
		jsonbuilder.add("sch:name", project.getProjectName());

		jsonbuilder.add("bot:hasBuilding", createJsonArrayBuildings(project.getBuilding()));

		return jsonbuilder.build();
	}
	
	public JsonObject generateContext() {
		JsonObjectBuilder jsonContextObjectBuilder = Json.createObjectBuilder();
		
		
		jsonContextObjectBuilder.add("sch", "http://schema.org/");
		jsonContextObjectBuilder.add("bot", "https://w3id.org/bot#");
		jsonContextObjectBuilder.add("sosa", "http://www.w3.org/ns/sosa/");
		jsonContextObjectBuilder.add("qu", "http://purl.org/NET/ssnx/qu/qu#");
		
		
		return jsonContextObjectBuilder.build();
	}

	private JsonArray createJsonArrayBuildings(List<Building> buildings) {
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
		
		jsonbuilder.add("sch:name", building.getType());

		jsonbuilder.add("sch:address", createJsonObjectAddress(building.getAddress()));

		jsonbuilder.add("bot:hasStorey", createJsonArrayStorey(building.getFloors()));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayStorey(List<Floor> floors) {
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
		
		//TODO ONTOLOGY
		jsonbuilder.add("number", floor.getFloorNumber());

		jsonbuilder.add("bot:hasSpace", createJsonArrayCorridors(floor.getCorridors()));

		jsonbuilder.add("bot:hasZone", createJsonArrayMotherRooms(floor.getMotherRooms()));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayMotherRooms(List<MotherRoom> motherRooms) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(MotherRoom motherRoom : motherRooms) {
			jsonArrayBuilder.add(createJsonObjectMotherRoom(motherRoom));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectMotherRoom(MotherRoom motherRoom) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Zone");

		jsonbuilder.add("@id", "Zone" + motherRoom.getId());
	
		//TODO ONTOLOGY
		jsonbuilder.add("number", motherRoom.getNumberMotherRoom());

		jsonbuilder.add("sch:name", motherRoom.getType());

		jsonbuilder.add("bot:hasSpace", mergeJsonArray(
				createJsonArrayCorridors(motherRoom.getCorridors()),
				createJsonArrayRooms(motherRoom.getRooms())
			)
		);

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayRooms(List<Room> rooms) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Room room : rooms) {
			jsonArrayBuilder.add(createJsonObjectRoom(room));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectRoom(Room room) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "bot:Space");

		jsonbuilder.add("@id", "Room" + room.getId());
		
		//TODO ONTOLOGY
		jsonbuilder.add("number", room.getNumberRoom());
		
		jsonbuilder.add("sch:name", room.getType());
		
		jsonbuilder.add("bot:hasElement", mergeJsonArray(
				createJsonArraySensors(room.getSensors()),
				createJsonArrayActuators(room.getActuators())
			)
		);

		return jsonbuilder.build();
	}
	
	private JsonArray createJsonArrayCorridors(List<Corridor> corridors) {
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
		
		jsonbuilder.add("sch:name", "Corridor");
		
		
		//TODO ONTOLOGY
		jsonbuilder.add("number", corridor.getNumberCorridor());
		
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

	private JsonArray createJsonArrayActuators(List<Actuator> actuators) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Actuator actuator : actuators) {
			jsonArrayBuilder.add(createJsonObjectActuator(actuator));
		}

		return jsonArrayBuilder.build();	
	}

	private JsonValue createJsonObjectActuator(Actuator actuator) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "sosa:Actuator");
		
		jsonbuilder.add("@id", "Actuator" + actuator.getId());
		
		jsonbuilder.add("sch:GeoCoordinates", Json.createObjectBuilder()
				.add("sch:latitude", actuator.getLatitude())
				.add("sch:longitude", actuator.getLatitude()).build());
		
		
		jsonbuilder.add("sch:brand", actuator.getBrand());
		
		jsonbuilder.add("sch:model", actuator.getModel());
		
		jsonbuilder.add("sch:gtin" + actuator.getReference().length(), actuator.getReference());

		return jsonbuilder.build();
	}

	private JsonArray createJsonArraySensors(List<Sensor> sensors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Sensor sensor : sensors) {
			jsonArrayBuilder.add(createJsonObjectSensor(sensor));
		}

		return jsonArrayBuilder.build();	}

	private JsonObject createJsonObjectSensor(Sensor sensor) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@type", "sosa:Sensor");
		
		jsonbuilder.add("@id", "Sensor" + sensor.getId());
		
		jsonbuilder.add("sch:GeoCoordinates", Json.createObjectBuilder()
				.add("sch:latitude", sensor.getLatitude())
				.add("sch:longitude", sensor.getLatitude()).build());
		
		
		//TODO A ajouter type
		//jsonbuilder.add("qu:QuantityKind", sensor.getType());
		
		jsonbuilder.add("sch:brand", sensor.getBrand());
		
		jsonbuilder.add("sch:model", sensor.getModel());
		
		jsonbuilder.add("sch:gtin" + sensor.getReference().length(), sensor.getReference());

		
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
