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
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
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
										.add("api", "/api")
										.add("description", "The informations about that api")
										.build()
								)
							.add(
									Json.createObjectBuilder()
										.add("buildings", "/api/buildings/")
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

		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("building", "/api/buildings/" + building.getId())
							.add("description", "The informations of a building")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("storeys", "/api/buildings/" + building.getId() + "/floors")
							.add("description", "The informations of all of floors of this building")
							.build()
					)
				.build()
		);
				
		
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
		
		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("storey", "/api/buildings/" + idBuilding + "/floors/" + floor.getId())
							.add("description", "The informations of that floor")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("corridors", pathOfCorridors)
							.add("description", "The informations about all of corridors of that storey")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("zones", pathOfMotherRooms )
							.add("description", "The informations about all of zones of that storey")
							.build()
					)
				.build()
				
		);
		
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
		
		jsonbuilder.add("zones", createJsonArrayMotherRooms(pathOfMotherRooms, floor.getMotherRooms()));
		
		
		return jsonbuilder.build();
	}

	private JsonArray createJsonArrayMotherRooms(String pathMotherRooms, Set<MotherRoom> motherRooms) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(MotherRoom motherRoom : motherRooms) {
			jsonArrayBuilder.add(createJsonObjectMotherRoom(pathMotherRooms + "/" + motherRoom.getId(), motherRoom));
		}

		return jsonArrayBuilder.build();
	}

	private JsonObject createJsonObjectMotherRoom(String pathMotherRoom, MotherRoom motherRoom) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfCorridors = pathMotherRoom + "/corridors";
		
		String pathOfRooms = pathMotherRoom + "/rooms";
		
		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("zone", pathMotherRoom)
							.add("description", "The informations of that zone")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("corridors", pathOfCorridors)
							.add("description", "The informations about all of corridors of that zone")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("rooms", pathOfRooms)
							.add("description", "The informations about all of rooms of that zone")
							.build()
					)
				.build()
				
		);
		
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
		
		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("room", pathRoom)
							.add("description", "The informations of that room")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("sensors", pathOfSensors)
							.add("description", "The informations about all of sensor of that room")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("actuators", pathOfActuators)
							.add("description", "The informations about all of actuator of that room")
							.build()
					)
				.build()
				
		);
		
		jsonbuilder.add("data", 
				Json.createObjectBuilder()
					.add("@type", "The value is semantic annotation to describe the room entity")
					.add("@id", "The value is an URI of that room")
					.add("number", "The number of that room in the zone")
					.add("sch:name", "The value is the name of the room")
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
		
		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("corridor", pathCorridor)
							.add("description", "The informations of that corridor")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("sensors", pathOfSensors)
							.add("description", "The informations about all of sensor of that corridor")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("actuators", pathOfActuators)
							.add("description", "The informations about all of actuator of that corridor")
							.build()
					)
				.build()
				
		);
		
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
			jsonArrayBuilder.add(createJsonObjectActuator(pathActuators + "/" + actuator.getId(), actuator));
		}

		return jsonArrayBuilder.build();	
	}

	private JsonValue createJsonObjectActuator(String pathActuator, Actuator actuator) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfDatas = pathActuator + "/datas";
		
		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("actuator", pathActuator)
							.add("description", "The informations of that actuator")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("actuations", pathOfDatas)
							.add("description", "The datas of that actuator")
							.build()
					)
				.build()				
		);
		
		jsonbuilder.add("data", 
				Json.createObjectBuilder()
					.add("@type", "The value is semantic annotation to describe the actuator entity")
					.add("@id", "The value is an URI of that actuator")
					.add("sch:GeoCoordinates", Json.createObjectBuilder()
							.add("sch:latitude", "The value is the latitude of the actuator")
							.add("sch:longitude", "The value is the longitude of the actuator").build())
					.add("sch:brand", "The value is the brand of the actuator")
					.add("sch:model", "The value is the model of the actuator")
					.add("sch:gtin" + actuator.getReference().length(), "The value is the gtin of the actuator")
					.add("sosa:madeActuation", "The actuation of the actuator")
					.build()
		);
		
		 
		jsonbuilder.add("actuations", createJsonObjectActuation(pathOfDatas));
		
		return jsonbuilder.build();
	}

	private JsonArray createJsonArraySensors(String pathSensors, Set<Sensor> sensors) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		for(Sensor sensor : sensors) {
			jsonArrayBuilder.add(createJsonObjectSensor(pathSensors + "/" + sensor.getId(), sensor));
		}

		return jsonArrayBuilder.build();	}

	private JsonObject createJsonObjectSensor(String pathSensor, Sensor sensor) {
		JsonObjectBuilder jsonbuilder = Json.createObjectBuilder();

		String pathOfDatas = pathSensor + "/datas";
		
		jsonbuilder.add("paths", Json.createArrayBuilder()
				.add(
						Json.createObjectBuilder()
							.add("sensor", pathSensor)
							.add("description", "The informations of that sensor")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("observations", pathOfDatas)
							.add("description", "The datas of that sensor")
							.build()
					)
				.build()				
		);
		
		jsonbuilder.add("data", 
				Json.createObjectBuilder()
					.add("@type", "The value is semantic annotation to describe the sensor entity")
					.add("@id", "The value is an URI of that sensor")
					.add("sch:GeoCoordinates", Json.createObjectBuilder()
							.add("sch:latitude", "The value is the latitude of the sensor")
							.add("sch:longitude", "The value is the longitude of the sensor").build())
					.add("sch:brand", "The value is the brand of the sensor")
					.add("sch:model", "The value is the model of the sensor")
					.add("sch:gtin" + sensor.getReference().length(), "The value is the gtin of the sensor")
					.add("qu:Unit", "The value is the unit of the sensor data")
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
							.add("observations", pathObservations)
							.add("description", "The observations of that sensor")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("observation", pathObservations + "/${id}")
							.add("description", "A data of that sensor")
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
							.add("actuations", pathActuation)
							.add("description", "The actuations of the actuator")
							.build()
					)
				.add(
						Json.createObjectBuilder()
							.add("actuation", pathActuation + "/${id}")
							.add("description", "A data of the actuator")
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
