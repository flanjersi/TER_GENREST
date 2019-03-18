const FusekiHTTP = require('./libs/fusekiHTTP.js');
const RequestsSPARQLHelper = require('./libs/requestSPARQLHelper.js');

var express = require('express');
var router = express.Router();

var fusekiHTTP = new FusekiHTTP();


<#assign buildings = project.building>

router.get('${urlProject}${project.projectName}', (req, res) => {
  res.send('OK for get project with name ${project.projectName}');
});


router.get('${urlProject}${project.projectName}/buildings/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getBuildings())
		.then(function(res){
			res.send(res);
		});
});

<#list buildings as building>
router.get('${urlProject}${project.projectName}/buildings/${building.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getBuilding(${building.id}))
		.then(function(res){
			res.send(res);
		});

});
<#assign floors = building.floors>

router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getFloorsOfBuilding(${building.id}))
		.then(function(res){
			res.send(res);
		});
});

<#list floors as floor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getFloor(${floor.id}))
		.then(function(res){
			res.send(res);
		});
});

<#assign corridorsFloor = floor.corridors>
<#-- if floor has corridor -->
<#if floor.corridors?has_content >
<#-- Get all corridors in floor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridors/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridorsOfFloor(${floor.id}))
		.then(function(res){
			res.send(res);
		});
});

<#list corridorsFloor as corridorFloor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridor(${corridorFloor.id}))
		.then(function(res){
			res.send(res);
		});
});

<#-- if corridorFloor has Sensors -->
<#if corridorFloor.sensors?has_content >
<#assign sensorsCorridorFloor = corridorFloor.sensors>
<#-- Get all Sensors in CorridorFloor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/sensors/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorsOfCorridor(${corridorFloor.id}))
		.then(function(res){
			res.send(res);
		});
});


<#list sensorsCorridorFloor as sensorCorridorFloor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/sensors/${sensorCorridorFloor.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensor(${sensorCorridorFloor.id}))
		.then(function(res){
			res.send(res);
		});
});
</#list>
</#if>

<#-- if corridorFloor has Actuators -->
<#if corridorFloor.actuators?has_content >
<#assign actuatorsCorridorFloor = corridorFloor.actuators>
<#-- Get all Actuators in CorridorFloor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/actuators/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorsOfCorridor(${corridorFloor.id}))
		.then(function(res){
			res.send(res);
		});
});


<#list actuatorsCorridorFloor as actuatorCorridorFloor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/actuators/${actuatorCorridorFloor.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuator(${actuatorCorridorFloor.id}))
		.then(function(res){
			res.send(res);
		});

});
</#list>
</#if>

</#list>
</#if>
<#-- if floor has motherRooms -->
<#if floor.motherRooms?has_content >
<#assign motherRooms = floor.motherRooms>
<#-- Get all motherRooms in Floor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getZonesOfFloor(${floor.id}))
		.then(function(res){
			res.send(res);
		});

});


<#list motherRooms as motherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getZone(${motherRoom.id}))
		.then(function(res){
			res.send(res);
		});
		
});


<#-- if motherRoom has Rooms -->
<#if motherRoom.rooms?has_content >
<#assign rooms = motherRoom.rooms>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getRoomsOfZone(${motherRoom.id}))
		.then(function(res){
			res.send(res);
		});

});


<#list rooms as room>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getRoom(${room.id}))
		.then(function(res){
			res.send(res);
		});
});


<#-- if Room has Sensors -->
<#if room.sensors?has_content >
<#assign sensors = room.sensors>
<#-- Get all sensors in room -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/sensors/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorsOfRoom(${room.id}))
		.then(function(res){
			res.send(res);
		});
});


<#list sensors as sensor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/sensors/${sensor.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensor(${sensor.id}))
		.then(function(res){
			res.send(res);
		});
});
</#list>

</#if>

<#-- if Room has Actuators -->
<#if room.actuators?has_content >
<#assign actuators = room.actuators>
<#-- Get all actuators in room -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/actuators/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorsOfRoom(${room.id}))
		.then(function(res){
			res.send(res);
		});
});


<#list actuators as actuator>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/actuators/${actuator.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuator(${actuator.id}))
		.then(function(res){
			res.send(res);
		});
});

</#list>
</#if>

</#list>
</#if>

<#-- if motherRoom has Corridors -->
<#if motherRoom.corridors?has_content >
<#assign corridorsMotherRoom = motherRoom.corridors>
<#-- Get all Corridors in motherRoom -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridors/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridorsOfZone(${motherRoom.id}))
		.then(function(res){
			res.send(res);
		});
});


<#list corridorsMotherRoom as corridorMotherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridor(${corridorMotherRoom.id}))
		.then(function(res){
			res.send(res);
		});
});


<#-- if corridorMotherRoom has Sensors -->
<#if corridorMotherRoom.sensors?has_content >
<#assign sensorscorridorsMotherRoom = corridorMotherRoom.sensors>
<#-- Get all Sensors in Corridors -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/sensors/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorsOfCorridor(${corridorMotherRoom.id}))
		.then(function(res){
			res.send(res);
		});
});


<#list sensorscorridorsMotherRoom as sensorcorridorsMotherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/sensors/${sensorcorridorsMotherRoom.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensor(${sensorcorridorsMotherRoom.id}))
		.then(function(res){
			res.send(res);
		});
});
</#list>
</#if>

<#-- if corridorMotherRoom has Actuators -->
<#if corridorMotherRoom.actuators?has_content >
<#assign actuatorscorridorsMotherRoom = corridorMotherRoom.actuators>
<#-- Get all actuators in CorridorMotherRooms -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/actuators/', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorsOfCorridor(${corridorMotherRoom.id}))
		.then(function(res){
			res.send(res);
		});

});


<#list actuatorscorridorsMotherRoom as actuatorcorridorsMotherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/actuators/${actuatorcorridorsMotherRoom.id}', (req, res) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuator(${actuatorcorridorsMotherRoom.id}))
		.then(function(res){
			res.send(res);
		});
});
</#list>
</#if>

</#list>
</#if>

</#list>
</#if>
</#list>

</#list>

// Export the router
module.exports = router;


