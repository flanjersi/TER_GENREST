package fr.amu.ter_genrest.services.generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Actuator;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.Zone;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.project_specifications.Sensor;

@Stateless
public class GenerationFileDescriptionAPIImpl implements GenerationFileDescriptionAPI{

	@Override
	public void generate(File fileDest, Project project) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("name", project.getProjectName());
		jsonbuilder.add("description", "Service web application in domotic domain used semantic annotation to describe data");
		jsonbuilder.add("provider", "GENREST");

		jsonbuilder.add("port", "3001");

		jsonbuilder.add("api", 
				Json.createObjectBuilder()
				.add("paths", Json.createArrayBuilder()
						.add(
								Json.createObjectBuilder()
								.add("value", "/api")
								.add("description", "The informations about that api")
								.build()
								)
						.add(
								Json.createObjectBuilder()
								.add("value", "/api/buildings/")
								.add("description", "The list informations of all buildings")
								.build()
								)
						.build()
						)
				.add("buildings", createJsonArrayBuildings(project.getBuilding())).build()	
				);


		try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileDest))) {
			writer.write(jsonbuilder.build().toString());
		} catch (IOException e) {
			e.printStackTrace();
		} 
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

		JsonArrayBuilder jsonPaths = Json.createArrayBuilder();


		jsonPaths.add(
				Json.createObjectBuilder()
				.add("value", "/api/buildings/" + building.getId())
				.add("description", "The informations of a building")
				.build()
				);
		
		if(!building.getFloors().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", "/api/buildings/" + building.getId() + "/floors")
					.add("description", "The informations of all of floors of this building")
					.build()
			);			
		}

		jsonbuilder.add("paths", jsonPaths.build());

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the building entity")
				.add("@id", "The value is an URI of the building")
				.add("sch:name", "The value is the name of the building")
				.add("sch:address", "The value is a object to describe with semantic annotation the address of the building")
				.add("bot:hasStorey", "The value is an array of URI which represent the storey of this building")
				.build()
				);

		jsonbuilder.add("storeys", createJsonArrayStorey(building.getId(), building.getFloors()));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayStorey(Long idbuilding, Set<Floor> floors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Floor floor : floors) {
			jsonArrayBuilder.add(createJsonObjectStorey(idbuilding, floor));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectStorey(Long idBuilding, Floor floor) {

		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfCorridors = "/api/buildings/" + idBuilding + "/floors/" + floor.getId() + "/corridors";

		String pathOfMotherRooms = "/api/buildings/" + idBuilding + "/floors/" + floor.getId() + "/zones";

		JsonArrayBuilder jsonPaths = Json.createArrayBuilder();
		jsonPaths.add(
				Json.createObjectBuilder()
				.add("value", "/api/buildings/" + idBuilding + "/floors/" + floor.getId())
				.add("description", "The informations of that floor")
				.build()
				);

		if(!floor.getCorridors().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfCorridors)
					.add("description", "The informations about all of corridors of that storey")
					.build()
					);

		}
		if(!floor.getZones().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfMotherRooms )
					.add("description", "The informations about all of zones of that storey")
					.build()
					);
		}


		jsonbuilder.add("paths", jsonPaths.build());

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the storey entity")
				.add("@id", "The value is an URI of that storey")
				.add("number", "The level of that storey in the building")
				.add("bot:hasSpace", "The value is an array of URI which represent the corridors of this storey")
				.add("bot:hasZone", "The value is an array of URI which represent zones of this storey, for example, an appartment")
				.build()
				);

		jsonbuilder.add("corridors", createJsonArrayCorridors(pathOfCorridors, floor.getCorridors()));

		jsonbuilder.add("zones", createJsonArrayMotherRooms(pathOfMotherRooms, floor.getZones()));


		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayMotherRooms(String pathMotherRooms, Set<Zone> motherRooms) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Zone motherRoom : motherRooms) {
			jsonArrayBuilder.add(createJsonObjectMotherRoom(pathMotherRooms + "/" + motherRoom.getId(), motherRoom));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectMotherRoom(String pathMotherRoom, Zone motherRoom) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfCorridors = pathMotherRoom + "/corridors";

		String pathOfRooms = pathMotherRoom + "/rooms";

		JsonArrayBuilder jsonPaths = Json.createArrayBuilder();

		jsonPaths.add(
				Json.createObjectBuilder()
				.add("value", pathMotherRoom)
				.add("description", "The informations of that zone")
				.build()
				);

		if(!motherRoom.getCorridors().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfCorridors)
					.add("description", "The informations about all of corridors of that zone")
					.build()
					);	
		}
		if(!motherRoom.getRooms().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfRooms)
					.add("description", "The informations about all of rooms of that zone")
					.build()
					);	
		}

		jsonbuilder.add("paths", jsonPaths.build());

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the zone entity")
				.add("@id", "The value is an URI of that zone")
				.add("number", "The number of that zone in the storey")
				.add("bot:hasSpace", "The value is an array of URI which represent the corridors and rooms of this storey")
				.build()
				);

		jsonbuilder.add("rooms", createJsonArrayRooms(pathOfRooms, motherRoom.getRooms()));

		jsonbuilder.add("corridors", createJsonArrayCorridors(pathOfCorridors, motherRoom.getCorridors()));


		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayRooms(String pathRooms, Set<Room> rooms) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Room room : rooms) {
			jsonArrayBuilder.add(createJsonObjectRoom(pathRooms + "/" + room.getId(), room));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectRoom(String pathRoom, Room room) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfSensors = pathRoom + "/sensors";

		String pathOfActuators = pathRoom + "/actuators";

		JsonArrayBuilder jsonPaths = Json.createArrayBuilder();


		jsonPaths.add(
				Json.createObjectBuilder()
				.add("value", pathRoom)
				.add("description", "The informations of that room")
				.build()
				);

		if(!room.getSensors().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfSensors)
					.add("description", "The informations about all of sensor of that room")
					.build()
					);	
		}

		if(!room.getActuators().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfActuators)
					.add("description", "The informations about all of actuator of that room")
					.build()
					);
		}


		jsonbuilder.add("paths", jsonPaths.build());


		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the room entity")
				.add("@id", "The value is an URI of that room")
				.add("rdfs:label", "The value is the name of the room")
				.add("bot:hasElement", "The value is an array of URI which represent the sensors and actuators of this room")
				.build()
				);

		jsonbuilder.add("sensors", createJsonArraySensors(pathOfSensors, room.getSensors()));

		jsonbuilder.add("actuators", createJsonArrayActuators(pathOfActuators, room.getActuators()));


		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayCorridors(String pathCorridors, Set<Corridor> corridors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Corridor corridor : corridors) {
			jsonArrayBuilder.add(createJsonObjectCorridor(pathCorridors + "/" + corridor.getId(), corridor));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectCorridor(String pathCorridor, Corridor corridor) {		
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfSensors = pathCorridor + "/sensors";

		String pathOfActuators = pathCorridor + "/actuators";

		JsonArrayBuilder jsonPaths = Json.createArrayBuilder();


		jsonPaths.add(
				Json.createObjectBuilder()
				.add("value", pathCorridor)
				.add("description", "The informations of that corridor")
				.build()
				);

		if(!corridor.getSensors().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfSensors)
					.add("description", "The informations about all of sensor of that corridor")
					.build()
					);	
		}

		if(!corridor.getActuators().isEmpty()) {
			jsonPaths.add(
					Json.createObjectBuilder()
					.add("value", pathOfActuators)
					.add("description", "The informations about all of actuator of that corridor")
					.build()
					);
		}


		jsonbuilder.add("paths", jsonPaths.build());

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the corridor entity")
				.add("@id", "The value is an URI of that corridor")
				.add("number", "The number of that corridor")
				.add("sch:name", "The value to indicate that entity is a corridor")
				.add("bot:hasElement", "The value is an array of URI which represent the sensors and actuators of this corridor")
				.build()
				);

		jsonbuilder.add("sensors", createJsonArraySensors(pathOfSensors, corridor.getSensors()));

		jsonbuilder.add("actuators", createJsonArrayActuators(pathOfActuators, corridor.getActuators()));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayActuators(String pathActuators, Set<Actuator> actuators) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Actuator actuator : actuators) {
			jsonArrayBuilder.add(createJsonObjectActuator(pathActuators + "/" + actuator.getName(), actuator));
		}

		return jsonArrayBuilder.build();	
	}

	private JsonValue createJsonObjectActuator(String pathActuator, Actuator actuator) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfDatas = pathActuator + "/datas";

		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
						.add("value", pathActuator)
						.add("description", "The informations of that actuator")
						.build()
						)
				.add(
						Json.createObjectBuilder()
						.add("value", pathOfDatas)
						.add("description", "The datas of that actuator")
						.build()
						)
				.build()				
				);

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the actuator entity")
				.add("@id", "The value is an URI of that actuator")
				.add("geo:Point", Json.createObjectBuilder()
						.add("geo:lat", "The value is the latitude of the actuator")
						.add("geo:long", "The value is the longitude of the actuator").build())
				.add("rdfs:label", "The value is the model of the actuator")
				.add("sosa:madeActuation", "The actuation of the actuator")
				.build()
				);


		jsonbuilder.add("actuations", createJsonObjectActuation(pathOfDatas));

		return jsonbuilder.build();
	}

	private JsonArray createJsonArraySensors(String pathSensors, Set<Sensor> sensors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Sensor sensor : sensors) {
			jsonArrayBuilder.add(createJsonObjectSensor(pathSensors + "/" + sensor.getName(), sensor));
		}

		return jsonArrayBuilder.build();	}

	private JsonObject createJsonObjectSensor(String pathSensor, Sensor sensor) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfDatas = pathSensor + "/datas";

		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
						.add("value", pathSensor)
						.add("description", "The informations of that sensor")
						.build()
						)
				.add(
						Json.createObjectBuilder()
						.add("value", pathOfDatas)
						.add("description", "The datas of that sensor")
						.build()
						)
				.build()				
				);

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe the sensor entity")
				.add("@id", "The value is an URI of that sensor")
				.add("geo:Point", Json.createObjectBuilder()
						.add("geo:lat", "The value is the latitude of the sensor")
						.add("geo:long", "The value is the longitude of the sensor").build())
				.add("rdfs:label", "The value is the model of the sensor")
				.add("qu:Unit", "The value is the unit of the sensor data")
				.add("qu:QuantityKind", "The value is the quantityKind of the sensor, temperature for example")
				.add("sosa:madeObservation", "The observations of the sensor")
				.build()
				);

		jsonbuilder.add("observations", createJsonObjectObservation(pathOfDatas));

		return jsonbuilder.build();
	}

	private JsonObject createJsonObjectObservation(String pathObservations) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
						.add("value", pathObservations)
						.add("description", "The observations of that sensor")
						.build()
						)
				.build()				
				);

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe a observation")
				.add("@id", "The value is an URI of that observation")
				.add("sosa:madeBySensor", "The URI of the sensor who get that data")
				.add("sosa:resultTime", "The date time where the observation is taken")
				.add("sosa:hasSimpleResult", "The value of the observation")		
				.build()
				);

		return jsonbuilder.build();
	}

	private JsonObject createJsonObjectActuation(String pathActuation) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
						.add("value", pathActuation)
						.add("description", "The actuations of the actuator")
						.build()
						)
				.build()	
				);

		jsonbuilder.add("data", 
				Json.createObjectBuilder()
				.add("@type", "The value is semantic annotation to describe a actuation")
				.add("@id", "The value is an URI of that actuation")
				.add("sosa:actuationMadeBy", "The URI of the actuator who get that data")
				.add("sosa:resultTime", "The date time where the observation is taken")
				.add("sosa:hasSimpleResult", "The value of the observation")		
				.build()
				);

		return jsonbuilder.build();
	}

}
