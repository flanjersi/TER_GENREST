var express = require('express');
var router = express.Router();

<#assign buildings = project.building>

router.get('${urlProject}${project.projectName}', (req, res) => {
  res.send('OK for get project with name ${project.projectName}');
});


router.get('${urlProject}${project.projectName}/buildings/', (req, res) => {
  res.send('OK for get all buildings');
});

<#list buildings as building>
router.get('${urlProject}${project.projectName}/buildings/${building.id}', (req, res) => {
  res.send('OK for get building with type ${building.type}');
});
<#assign floors = building.floors>

router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/', (req, res) => {
  res.send('OK for get all floors');
});

<#list floors as floor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}', (req, res) => {
  res.send('OK for get floor with floorNumber ${floor.floorNumber}');
});

<#assign corridorsFloor = floor.corridors>
<#-- if floor has corridor -->
<#if floor.corridors?has_content >
<#-- Get all corridors in floor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridors/', (req, res) => {
  res.send('OK for get all corridor in floor');
});
<#list corridorsFloor as corridorFloor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}', (req, res) => {
  res.send('OK for get corridorFloor with numberCorridor ${corridorFloor.numberCorridor}');
});

<#-- if corridorFloor has Sensors -->
<#if corridorFloor.sensors?has_content >
<#assign sensorsCorridorFloor = corridorFloor.sensors>
<#-- Get all Sensors in CorridorFloor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/sensors/', (req, res) => {
  res.send('OK for get all sensors in corridorFloor');
});
<#list sensorsCorridorFloor as sensorCorridorFloor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/sensors/${sensorCorridorFloor.id}', (req, res) => {
  res.send('OK for get sensor in corridorFloor with id ${sensorCorridorFloor.id}');
});
</#list>
</#if>

<#-- if corridorFloor has Actuators -->
<#if corridorFloor.actuators?has_content >
<#assign actuatorsCorridorFloor = corridorFloor.actuators>
<#-- Get all Actuators in CorridorFloor -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/actuators/', (req, res) => {
  res.send('OK for get all actuators in corridorFloor');
});
<#list actuatorsCorridorFloor as actuatorCorridorFloor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/corridor/${corridorFloor.id}/sensors/${actuatorCorridorFloor.id}', (req, res) => {
  res.send('OK for get actuator in corridorFloor with id ${actuatorCorridorFloor.id}');
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
  res.send('OK for get all motherRooms in floor');
});
<#list motherRooms as motherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}', (req, res) => {
  res.send('OK for get motherRoom ${motherRoom.type}');
});
<#-- if motherRoom has Rooms -->
<#if motherRoom.rooms?has_content >
<#assign rooms = motherRoom.rooms>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms', (req, res) => {
  res.send('OK for all get rooms in motherRoom');
});
<#list rooms as room>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}', (req, res) => {
  res.send('OK for get room numberRoom ${room.numberRoom}');
});
<#-- if Room has Sensors -->
<#if room.sensors?has_content >
<#assign sensors = room.sensors>
<#-- Get all sensors in room -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/sensors/', (req, res) => {
  res.send('OK for get all sensors in room');
});

<#list sensors as sensor>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/sensors/${sensor.id}', (req, res) => {
  res.send('OK for get sensor in room with id ${sensor.id}');
});
</#list>

</#if>

<#-- if Room has Actuators -->
<#if room.actuators?has_content >
<#assign actuators = room.actuators>
<#-- Get all actuators in room -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/actuators/', (req, res) => {
  res.send('OK for get all actuators in room');
});

<#list actuators as actuator>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/rooms/${room.id}/actuators/${actuator.id}', (req, res) => {
  res.send('OK for get actuator in room with id ${actuator.id}');
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
  res.send('OK for get all corridors in motherRoom');
});
<#list corridorsMotherRoom as corridorMotherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}', (req, res) => {
  res.send('OK for get corridor in MotherRoom with id ${corridorMotherRoom.id}');
});
<#-- if corridorMotherRoom has Sensors -->
<#if corridorMotherRoom.sensors?has_content >
<#assign sensorscorridorsMotherRoom = corridorMotherRoom.sensors>
<#-- Get all Sensors in Corridors -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/sensors/', (req, res) => {
  res.send('OK for get all sensors in corridorMotherRoom');
});
<#list sensorscorridorsMotherRoom as sensorcorridorsMotherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/sensors/${sensorcorridorsMotherRoom.id}', (req, res) => {
  res.send('OK for get sensor in corridorMotherRoom with id ${sensorcorridorsMotherRoom.id}');
});
</#list>
</#if>

<#-- if corridorMotherRoom has Actuators -->
<#if corridorMotherRoom.actuators?has_content >
<#assign actuatorscorridorsMotherRoom = corridorMotherRoom.actuators>
<#-- Get all actuators in CorridorMotherRooms -->
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/actuators/', (req, res) => {
  res.send('OK for get all actuators in corridorMotherRoom');
});
<#list actuatorscorridorsMotherRoom as actuatorcorridorsMotherRoom>
router.get('${urlProject}${project.projectName}/buildings/${building.id}/floors/${floor.id}/zone/${motherRoom.id}/corridor/${corridorMotherRoom.id}/sensors/${actuatorcorridorsMotherRoom.id}', (req, res) => {
  res.send('OK for get actuator in corridorMotherRoom with id ${actuatorcorridorsMotherRoom.id}');
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


