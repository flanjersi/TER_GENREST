package fr.amu.terGENREST.services.generation;

import java.util.List;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.projectSpecifications.Actuator;
import fr.amu.terGENREST.entities.projectSpecifications.Address;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.projectSpecifications.Room;
import fr.amu.terGENREST.entities.projectSpecifications.Sensor;

@Stateless
public class GenerationJsonLDImpl implements GenerationJsonLD{

	@Override
	public JsonObject generateJsonLDDataFile(Project project) {

		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		//TODO ONTOLOGY
		jsonbuilder.add("projectName", project.getProjectName());

		//TODO ONTOLOGY
		jsonbuilder.add("buildings", createJsonArrayBuildings(project.getBuilding()));

		return jsonbuilder.build();
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

		//TODO ONTOLOGY
		jsonbuilder.add("type", building.getType());

		//TODO ONTOLOGY
		jsonbuilder.add("address", createJsonObjectAddress(building.getAddress()));

		//TODO ONTOLOGY
		jsonbuilder.add("storey", createJsonArrayStorey(building.getFloors()));

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

		//TODO ONTOLOGY
		jsonbuilder.add("number", floor.getFloorNumber());

		//TODO ONTOLOGY
		jsonbuilder.add("corridors", createJsonArrayCorridors(floor.getCorridors()));

		//TODO ONTOLOGY
		jsonbuilder.add("motherRooms", createJsonArrayMotherRooms(floor.getMotherRooms()));

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

		//TODO ONTOLOGY
		jsonbuilder.add("number", motherRoom.getNumberMotherRoom());

		//TODO ONTOLOGY
		jsonbuilder.add("type", motherRoom.getType());

		//TODO ONTOLOGY
		jsonbuilder.add("corridors", createJsonArrayCorridors(motherRoom.getCorridors()));

		jsonbuilder.add("rooms", createJsonArrayRooms(motherRoom.getRooms()));

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

		jsonbuilder.add("number", room.getNumberRoom());
		jsonbuilder.add("type", room.getType());

		jsonbuilder.add("sensors", createJsonArraySensors(room.getSensors()));

		jsonbuilder.add("actuators", createJsonArrayActuators(room.getActuators()));


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

		//TODO ONTOLOGY
		jsonbuilder.add("latitude", actuator.getLatitude());
		
		//TODO ONTOLOGY
		jsonbuilder.add("longitude", actuator.getLongitude());
		
		//TODO ONTOLOGY
		jsonbuilder.add("brand", actuator.getBrand());
		
		//TODO ONTOLOGY
		jsonbuilder.add("model", actuator.getModel());
		
		//TODO ONTOLOGY
		jsonbuilder.add("reference", actuator.getReference());

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

		//TODO ONTOLOGY
		jsonbuilder.add("latitude", sensor.getLatitude());
		
		//TODO ONTOLOGY
		jsonbuilder.add("longitude", sensor.getLongitude());
		
		//TODO ONTOLOGY
		jsonbuilder.add("brand", sensor.getBrand());
		
		//TODO ONTOLOGY
		jsonbuilder.add("model", sensor.getModel());
		
		//TODO ONTOLOGY
		jsonbuilder.add("reference", sensor.getReference());
		
		//TODO ONTOLOGY
		jsonbuilder.add("unitData", sensor.getUnitData());

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

		//TODO ONTOLOGY
		jsonbuilder.add("number", corridor.getNumberCorridor());
		
		//TODO ONTOLOGY
		jsonbuilder.add("actuators", createJsonArrayActuators(corridor.getActuators()));
		
		//TODO ONTOLOGY
		jsonbuilder.add("sensors", createJsonArraySensors(corridor.getSensors()));

		return jsonbuilder.build();
	}

	private JsonObject createJsonObjectAddress(Address address) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("@context", "https://schema.org/");
		jsonbuilder.add("@type", "PostalAddress");
		
		jsonbuilder.add("addressCountry", address.getCountry());
		jsonbuilder.add("addressLocality", address.getCity());
		jsonbuilder.add("streetAddress", address.getStreet());

		return jsonbuilder.build();
	}



}
