const FusekiHTTP = require('./libs/fusekiHTTP.js');
const RequestsSPARQLHelper = require('./libs/requestsSPARQLHelper.js');

const descriptionAPI = require('./descriptionAPI.json');

var express = require('express');
var router = express.Router();

var fusekiHTTP = new FusekiHTTP();


<#assign buildings = project.building>

router.get('', (req, resp) => {
	resp.send('Lunch the same request in the options http method to get details about this web service');
});

var apiDescription = descriptionAPI['api'];

router.options('', (req, resp) => {
	resp.send(apiDescription);
});

router.get('/buildings/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getBuildings())
		.then(function(res){
			resp.send(res);
		});
});

var descriptionBuildings = apiDescription['buildings'];

router.options('/buildings/', (req, resp) => {
	resp.send(descriptionBuildings);
})

<#list buildings as building>


router.get('/buildings/${building.id}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getBuilding(${building.id}))
		.then(function(res){
			resp.send(res);
		});

});

var descriptionBuilding${building.id} = descriptionBuildings.filter(elt =>
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}'
	).length == 1
)[0];


router.options('/buildings/${building.id}', (req, resp) => {
	resp.send(descriptionBuilding${building.id});
});


<#assign floors = building.floors>


router.get('/buildings/${building.id}/floors/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getFloorsOfBuilding(${building.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionStoreysOfBuilding${building.id} = descriptionBuilding${building.id}['storeys'];

router.options('/buildings/${building.id}/floors/', (req, resp) => {
	resp.send(descriptionStoreysOfBuilding${building.id});
});


<#list floors as floor>


router.get('/buildings/${building.id}/floors/${floor.id}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getFloor(${floor.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionStorey${floor.id} = descriptionStoreysOfBuilding${building.id}.filter(elt =>
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}', (req, resp) => {
	resp.send(descriptionStorey${floor.id});
});


<#assign corridorsFloor = floor.corridors>
<#-- if floor has corridor -->
<#if corridorsFloor?size gt 0>
<#-- Get all corridors in floor -->
router.get('/buildings/${building.id}/floors/${floor.id}/corridors/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridorsOfFloor(${floor.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionCorridorsOfStorey${floor.id} = descriptionStorey${floor.id}['corridors'];

router.options('/buildings/${building.id}/floors/${floor.id}/corridors/', (req, resp) => {
	resp.send(descriptionCorridorsOfStorey${floor.id});
});

<#list corridorsFloor as corridorFloor>
router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridor(${corridorFloor.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionCorridor${corridorFloor.id}OfStorey${floor.id} = descriptionCorridorsOfStorey${floor.id}.filter(elt =>
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}', (req, resp) => {
	resp.send(descriptionCorridor${corridorFloor.id}OfStorey${floor.id});
});


<#-- if corridorFloor has Sensors -->
<#if corridorFloor.sensors?size gt 0 >
<#assign sensorsCorridorFloor = corridorFloor.sensors>
<#-- Get all Sensors in CorridorFloor -->
router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/sensors/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorsOfCorridor(${corridorFloor.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionSensorsOfCorridor${corridorFloor.id} = descriptionCorridor${corridorFloor.id}OfStorey${floor.id}['sensors'];

router.options('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/sensors/', (req, resp) => {
	resp.send(descriptionSensorsOfCorridor${corridorFloor.id});
});


<#list sensorsCorridorFloor as sensorCorridorFloor>
router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/sensors/${sensorCorridorFloor.name}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensor("${sensorCorridorFloor.name}"))
		.then(function(res){
			resp.send(res);
		});
});


var descriptionSensor${sensorCorridorFloor.name}OfCorridor${corridorFloor.id} = descriptionSensorsOfCorridor${corridorFloor.id}.filter(elt =>
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/sensors/${sensorCorridorFloor.name}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/sensors/${sensorCorridorFloor.name}', (req, resp) => {
	resp.send(descriptionSensor${sensorCorridorFloor.name}OfCorridor${corridorFloor.id});
});

router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/sensors/${sensorCorridorFloor.name}/datas', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorObservations("${sensorCorridorFloor.name}"))
		.then(function(res){
			resp.send(res);
		});
});

</#list>
</#if>

<#-- if corridorFloor has Actuators -->
<#if corridorFloor.actuators?size gt 0 >
<#assign actuatorsCorridorFloor = corridorFloor.actuators>
<#-- Get all Actuators in CorridorFloor -->
router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/actuators/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorsOfCorridor(${corridorFloor.id}))
		.then(function(res){
			resp.send(res);
		});
});


var descriptionActuatorsOfCorridor${corridorFloor.id} = descriptionCorridor${corridorFloor.id}OfStorey${floor.id}['actuators'];

router.options('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/actuators/', (req, resp) => {
	resp.send(descriptionActuatorsOfCorridor${corridorFloor.id});
});

<#list actuatorsCorridorFloor as actuatorCorridorFloor>
router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/actuators/${actuatorCorridorFloor.name}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuator("${actuatorCorridorFloor.name}"))
		.then(function(res){
			resp.send(res);
		});

});

var descriptionActuator${actuatorCorridorFloor.name}OfCorridor${corridorFloor.id} = descriptionActuatorsOfCorridor${corridorFloor.id}.filter(elt =>
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/actuators/${actuatorCorridorFloor.name}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/actuators/${actuatorCorridorFloor.name}', (req, resp) => {
	resp.send(descriptionActuator${actuatorCorridorFloor.name}OfCorridor${corridorFloor.id});
});

router.get('/buildings/${building.id}/floors/${floor.id}/corridors/${corridorFloor.id}/actuators/${actuatorCorridorFloor.name}/datas', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorActuations("${actuatorCorridorFloor.name}"))
		.then(function(res){
			resp.send(res);
		});

});

</#list>
</#if>

</#list>
</#if>
<#-- if floor has zones -->
<#if floor.zones?size gt 0 >
<#assign zones = floor.zones>
<#-- Get all zones in Floor -->
router.get('/buildings/${building.id}/floors/${floor.id}/zones/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getZonesOfFloor(${floor.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionZonesOfStorey${floor.id} = descriptionStorey${floor.id}['zones'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/', (req, resp) => {
	resp.send(descriptionZonesOfStorey${floor.id});
});


<#list zones as zone>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getZone(${zone.id}))
		.then(function(res){
			resp.send(res);
		});
		
});

var descriptionZone${zone.id}OfStorey${floor.id} = descriptionZonesOfStorey${floor.id}.filter(elt =>
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}', (req, resp) => {
	resp.send(descriptionZone${zone.id}OfStorey${floor.id});
});


<#-- if zone has Rooms -->
<#if zone.rooms?size gt 0 >
<#assign rooms = zone.rooms>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getRoomsOfZone(${zone.id}))
		.then(function(res){
			resp.send(res);
		});

});

var descriptionRoomsOfZone${zone.id} = descriptionZone${zone.id}OfStorey${floor.id}['rooms'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms', (req, resp) => {
	resp.send(descriptionRoomsOfZone${zone.id});
});


<#list rooms as room>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getRoom(${room.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionRoom${room.id}OfZone${zone.id} = descriptionRoomsOfZone${zone.id}.filter(elt => 
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}', (req, resp) => {
	resp.send(descriptionRoom${room.id}OfZone${zone.id});
});

<#-- if Room has Sensors -->
<#if room.sensors?size gt 0 >
<#assign sensors = room.sensors>
<#-- Get all sensors in room -->
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/sensors/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorsOfRoom(${room.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionSensorsOfRoom${room.id} = descriptionRoom${room.id}OfZone${zone.id}['sensors'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/sensors/', (req, resp) => {
	resp.send(descriptionSensorsOfRoom${room.id});
});


<#list sensors as sensor>

router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/sensors/${sensor.name}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensor("${sensor.name}"))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionSensor${sensor.name}OfRoom${room.id} = descriptionSensorsOfRoom${room.id}.filter(elt => 
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/sensors/${sensor.name}'
	).length == 1
)[0];

router.options('/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/sensors/${sensor.name}', (req, resp) => {
	resp.send(descriptionSensor${sensor.name}OfRoom${room.id});
});


router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/sensors/${sensor.name}/datas', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorObservations("${sensor.name}"))
		.then(function(res){
			resp.send(res);
		});
});

</#list>

</#if>

<#-- if Room has Actuators -->
<#if room.actuators?size gt 0 >
<#assign actuators = room.actuators>
<#-- Get all actuators in room -->


router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/actuators/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorsOfRoom(${room.id}))
		.then(function(res){
			resp.send(res);
		});
});


var descriptionActuatorsOfRoom${room.id} = descriptionRoom${room.id}OfZone${zone.id}['actuators'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/actuators/', (req, resp) => {
	resp.send(descriptionActuatorsOfRoom${room.id});
});

<#list actuators as actuator>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/actuators/${actuator.name}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuator("${actuator.name}"))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionActuator${actuator.name}OfRoom${room.id} = descriptionActuatorsOfRoom${room.id}.filter(elt => 
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/actuators/${actuator.name}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/actuators/${actuator.name}', (req, resp) => {
	resp.send(descriptionActuator${actuator.name}OfRoom${room.id});
});

router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/rooms/${room.id}/actuators/${actuator.name}/datas', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorActuations("${actuator.name}"))
		.then(function(res){
			resp.send(res);
		});
});

</#list>
</#if>

</#list>
</#if>

<#-- if zone has Corridors -->
<#if zone.corridors?size gt 0 >
<#assign corridorsZone = zone.corridors>
<#-- Get all Corridors in zone -->
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridorsOfZone(${zone.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionCorridorsOfZone${zone.id} = descriptionZone${zone.id}OfStorey${floor.id}['corridors'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/', (req, resp) => {
	resp.send(descriptionCorridorsOfZone${zone.id});
});

<#list corridorsZone as corridorZone>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getCorridor(${corridorZone.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionCorridor${corridorZone.id}OfZone${zone.id} = descriptionCorridorsOfZone${zone.id}.filter(elt => 
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}', (req, resp) => {
	resp.send(descriptionCorridor${corridorZone.id}OfZone${zone.id});
});


<#-- if corridorZone has Sensors -->
<#if corridorZone.sensors?size gt 0 >
<#assign sensorsCorridorsZone = corridorZone.sensors>
<#-- Get all Sensors in Corridors -->
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/sensors/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorsOfCorridor(${corridorZone.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionSensorsOfCorridor${corridorZone.id} = descriptionCorridor${corridorZone.id}OfZone${zone.id}['sensors'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/sensors/', (req, resp) => {
	resp.send(descriptionSensorsOfCorridor${corridorZone.id});
});


<#list sensorsCorridorsZone as sensorCorridorsZone>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/sensors/${sensorCorridorsZone.name}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensor("${sensorCorridorsZone.name}"))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionSensor${sensorCorridorsZone.id}OfCorridor${corridorZone.id} = descriptionSensorsOfCorridor${corridorZone.id}.filter(elt => 
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/sensors/${sensorCorridorsZone.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/sensors/${sensorCorridorsZone.id}', (req, resp) => {
	resp.send(descriptionSensor${sensorCorridorsZone.id}OfCorridor${corridorZone.id});
});

router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/sensors/${sensorCorridorsZone.name}/datas', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getSensorObservations("${sensorCorridorsZone.name}"))
		.then(function(res){
			resp.send(res);
		});
});

</#list>
</#if>

<#-- if corridorZone has Actuators -->
<#if corridorZone.actuators?size gt 0 >
<#assign actuatorsCorridorZone = corridorZone.actuators>
<#-- Get all actuators in CorridorZone -->
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/actuators/', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorsOfCorridor(${corridorZone.id}))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionActuatorsOfCorridor${corridorZone.id} = descriptionCorridor${corridorZone.id}OfZone${zone.id}['actuators'];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/actuators/', (req, resp) => {
	resp.send(descriptionActuatorsOfCorridor${corridorZone.id});
});



<#list actuatorsCorridorZone as actuatorCorridorsZone>
router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/actuators/${actuatorCorridorsZone.name}', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuator("${actuatorCorridorsZone.name}"))
		.then(function(res){
			resp.send(res);
		});
});

var descriptionActuator${actuatorCorridorsZone.id}OfCorridor${corridorZone.id} = descriptionActuatorsOfCorridor${corridorZone.id}.filter(elt => 
	elt.paths.filter(path => 
		path.value === '/api/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/actuators/${actuatorCorridorsZone.id}'
	).length == 1
)[0];

router.options('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/actuators/${actuatorCorridorsZone.name}', (req, resp) => {
	resp.send(descriptionActuator${actuatorCorridorsZone.id}OfCorridor${corridorZone.id});
});

router.get('/buildings/${building.id}/floors/${floor.id}/zones/${zone.id}/corridors/${corridorZone.id}/actuators/${actuatorCorridorsZone.name}/datas', (req, resp) => {
	fusekiHTTP.query('genrest', RequestsSPARQLHelper.getActuatorActuations("${actuatorCorridorsZone.name}"))
		.then(function(res){
			resp.send(res);
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


