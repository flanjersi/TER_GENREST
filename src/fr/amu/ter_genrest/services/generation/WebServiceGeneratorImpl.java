package fr.amu.ter_genrest.services.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project_specifications.ActuatorManager;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.user.UserManager;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Stateless
public class WebServiceGeneratorImpl implements WebServiceGenerator {

	public Template templateRoute = null;
	private Template templateServer = null;
	Map<String, Object> dataMapRoutes = new HashMap<String, Object>();
	Map<String, Object> dataMapServer = new HashMap<String, Object>();
	ArrayList<Long> idProjects = new ArrayList<Long>();
	ArrayList<Long> idBuildings = new ArrayList<Long>();

//	Language langage = new Language();
//	fr.amu.ter_genrest.entities.environment_technical.Configuration configuration = new fr.amu.ter_genrest.entities.environment_technical.Configuration();

	public final static String TEMPLATEROUTE = "WebContent/templates/expressjs/RoutesTemplate.ftl";
	//public final static String TEMPLATESERVER = "WebContent/templates/expressjs/ServerTemplate.ftl";

	public final static String URLPROJECTS = "http://localhost:8090/terGENREST/api/users/";
	public WebServiceGeneratorImpl() {
		// init();
	}

	public void init() {

		Configuration cfg = new Configuration();
		try {
			templateRoute = cfg.getTemplate(TEMPLATEROUTE);
			//templateServer = cfg.getTemplate(TEMPLATESERVER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Map<String, Object> buildDataRoutes(User user, Language language, 
			fr.amu.ter_genrest.entities.environment_technical.Configuration configuration, OperatingSystem operatingSystem ) {
		
		dataMapRoutes.put("urlProject", URLPROJECTS+user.getId()+"/projects/");
		
		for (Project project : user.getProjects()) {
			System.out.println("/////////////////////// "+project.getId());
			idProjects.add(project.getId());
//           	  for (Building building: project.getBuilding()) {
//               	  idBuildings.add(building.getId());
// 	                for (Floor floor: building.getFloors()) {
//	                	  idFloors.add(floor.getId());
//	                	  for (Corridor corridor: floor.getCorridors()) { 
//		                	  idCorridors.add(corridor.getId());
//		                	  for (Sensor sensor: corridor.getSensors()) { 
//			                	  idSensors.add(sensor.getId());
//			                	  for (Actuator actuator: corridor.getActuators()) { 
//				                	  idActuators.add(actuator.getId());
//			                	  }
//		                	  }
//		                	}
//	                	  dataMapRoutes.put("idFloors", idFloors);
//			              dataMapRoutes.put("idCorridors", idCorridors);
//			              dataMapRoutes.put("idSensors", idCorridors);
//			              dataMapRoutes.put("idActuators", idCorridors);
//		                	  
//		                	  for (MotherRoom motherRoom: floor.getMotherRooms()) { 
//			                	  idMotherRooms.add(motherRoom.getId())
//			                	  
//		                	  }				      
//		                }         	  
// 	                }
//           	  }
		}
		System.out.println(idProjects.size());
		dataMapRoutes.put("idProjects", idProjects);
		
		//dataMapRoutes.put("idBuildings", idBuildings);
		// dataMapRoutes.put("idFloors", idFloors);

		// dataMapRoutes.put("idMotherRooms", idMotherRooms);

		return dataMapRoutes;
	}

	public Map<String, Object> buildDataServer(User user, Language language, 
			fr.amu.ter_genrest.entities.environment_technical.Configuration configuration, OperatingSystem operatingSystem ) {

		return dataMapServer;
	}

	public void writeRoutesFile() {
		Writer file = null;
		try {
			File routes = new File("Generated/Routes.js");
			if(routes.delete()){
				System.out.println("Routes.js file deleted");
			}
			file = new FileWriter(new File("Generated/Routes.js"));
			templateRoute.process(dataMapRoutes, file);
			file.flush();
			System.out.println("Success");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void writeServerFile() {
		Writer file = null;
		try {
			// file = new FileWriter (new File("Generated/Server.js"));
			// templateServer.process(dataMapServer, file);
			// file.flush();
			System.out.println("Success");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}

/* Code templates des routes 
<#-- Get all buildings -->
router.get(${urlProject}.''.${idProject}.'/buildings/', (req, res) => {
  res.render('OK for all get building');
	});

<#-- Get all floors -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/', (req, res) => {
  res.render('OK for all get floor');
	});

<#-- Get all corridors -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/', (req, res) => {
  res.render('OK for all get Corridor');
	});

<#-- Get all motherRooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/', (req, res) => {
  res.render('OK for all get motherRooms');
	});

<#-- Get all corridor in motherRooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idmotherRooms}.'/corridors/', (req, res) => {
  res.render('OK for all get motherRooms');
	});

<#-- Get all actuators in corridor in motherRooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idmotherRooms}.'/corridors/'.${idCorridor}.'/actuators/', (req, res) => {
  res.render('OK for all get motherRooms');
	});
	
<#-- Get all sensors in corridor in motherRooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idmotherRooms}.'/corridors/'.${idCorridor}.'/actuators/', (req, res) => {
  res.render('OK for all get motherRooms');
	});	

<#-- Get all actuators in corridor -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.${idCorridor}.'/actuators/', (req, res) => {
  res.render('OK for get all actuators in Corridor');
	});

<#-- Get all sensors in corridor-->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.${idCorridor}.'/sensors/', (req, res) => {
  res.render('OK for get all Sensors');
	});

<#-- Get all rooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.idMotherRoom.'/rooms/', (req, res) => {
  res.render('OK for all get rooms');
	});

<#-- Get all sensors in rooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.idMotherRoom.'/rooms/'.${idRoom}.'/sensors/', (req, res) => {
  res.render('OK for get all sensors in rooms');
	});
	
<#-- Get all actuators in rooms -->
router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.idMotherRoom.'/rooms/'.${idRoom}.'/actuators/', (req, res) => {
  res.render('OK for get all sensors in rooms');
	});
	
	
<#---------------------------- Specific element ----------------------------------------------------------->	

<#list $idProjects as idProject>
  router.get( ${urlProject}, (req, res) => {
  res.render('OK for get project with id '.idProject);
	});
</#list>

<#list $idBuildings as idBuilding>
  router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}, (req, res) => {
  res.render('OK for get building with id '.idBuilding);
	});
</#list>

<#list $idFloors as idFloor>
  router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}, (req, res) => {
  res.render('OK for get floor with id '.idFloor);
	});
</#list>

<#list $idCorridorsFloors as idCorridorFloor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.${idCorridorFloor}, (req, res) => {
  res.render('OK for get Corridor with id '.idCorridorFloor);
	});
</#list>

<#list $idMotherRoomsFloors as idMotherRoomFloor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idMotherRoomFloor}, (req, res) => {
  res.render('OK for get MotherRoom with id '.idMotherRoomFloor);
	});
</#list>

<#list $idRoomsMotherRoomFloor as idRoomMotherRoomFloor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idMotherRoomsFloor}.'/rooms/'.idRoomMotherRoomFloor, (req, res) => {
  res.render('OK for get Room with id '.idRoomMotherRoomFloor);
	});
</#list>

<#list $idActuatorsRoomMotherRoomFloor as idActuatorRoomMotherRoomFloor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idMotherRoomsFloor}.'/rooms/'.idRoomMotherRoomFloor.'/actuators/'.idActuatorRoomMotherRoomFloor, (req, res) => {
  res.render('OK for get Room with id '.idActuatorRoomMotherRoomFloor);
	});
</#list>

<#list $idSensorsRoomMotherRoomFloor as idSensorRoomMotherRoomFloor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/motherRooms/'.${idMotherRoomsFloor}.'/rooms/'.idRoomMotherRoomFloor.'/sensors/'.idSensorRoomMotherRoomFloor, (req, res) => {
  res.render('OK for get Room with id '.idSensorRoomMotherRoomFloor);
	});
</#list>

<#list $idMotherRoomsCorridor as idMotherRoomsCorridor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms, (req, res) => {
  res.render('OK for get MotherRoom with id '.idMotherRoomsCorridor);
	});
</#list>

<#list $idRoomsMotherRoomCorridor as idRoomMotherRoomCorridor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/rooms/'.idRoomMotherRoomCorridor, (req, res) => {
  res.render('OK for get room with id '.idRoomMotherRoomCorridor);
	});
</#list>

<#list $idActuatorsRoomMotherRoomCorridor as idActuatorRoomMotherRoomCorridor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/rooms/'.idRoomMotherRoomCorridor.'/actuators/'.idActuatorsRoomMotherRoomCorridor, (req, res) => {
  res.render('OK for get Actuators with id '.idActuatorsRoomMotherRoomCorridor);
	});
</#list>

<#list $idSensorsRoomMotherRoomCorridor as idSensorRoomMotherRoomCorridor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/rooms/'.idRoomMotherRoomCorridor.'/sensors/'.idSensorRoomMotherRoomCorridor, (req, res) => {
  res.render('OK for get Sensors with id '.idSensorRoomMotherRoomCorridor);
	});
</#list>

<#list $idRooms as idRoom>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/rooms/'.${idRoom}, (req, res) => {
  res.render('OK for get project with id ');
	});
</#list>

<#list $idSensors as idSensor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/rooms/'.${idRoom}.'/sensors/'.${idSensor}, (req, res) => {
  res.render('OK for get project with id ');
	});
</#list>

<#list $idActuatuors as idActuatuor>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/rooms/'.${idRoom}.'/actuators/'.${idActuatuor}, (req, res) => {
  res.render('OK for get project with id ');
	});
</#list>

<#list $idCorridorsMotherRooms as idCorridorsMotherRoom>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/corridors/'.${idCorridorsMotherRooms}, (req, res) => {
  res.render('OK for get project with id ');
	});
</#list>

<#list $idActuatorsCorridorsMotherRooms as idActuatorsCorridorsMotherRoom>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/corridors/'.${idCorridors}.'/actuators/'.idActuatorsCorridorsMotherRooms, (req, res) => {
  res.render('OK for get project with id ');
	});
</#list>

<#list $idSensorsCorridorsMotherRooms as idSensorsCorridorsMotherRooms>
   router.get(${urlProject}.''.${idProject}.'/buildings/'.${idBuilding}.'/floors/'.${idFloor}.'/corridors/'.idCorridors.'/motherRooms/'.idMotherRooms.'/corridors/'.${idCorridors}.'/sensors/'.idSensorsCorridorsMotherRooms, (req, res) => {
  res.render('OK for get project with id ');
	});
</#list>

 * */
