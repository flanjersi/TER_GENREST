
module.exports = class RequestSPARQLHelpers {

    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * ----------------------------- BUILDINGS -----------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */

    static getBuildings(){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX sch: <http://schema.org/>" +
            "CONSTRUCT { " +
            "  ?s ?p ?o." +
            "  ?a ?ap ?ao." +
            "}" +
            "WHERE {" +
            "  ?s a bot:Building;" +
            "      ?p ?o;" +
            "      sch:address ?a." +
            "  ?a ?ap ?ao." +
            "}";
    }

    static getBuilding(buildingId){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX sch: <http://schema.org/>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Building" + buildingId + "> ?p ?o." +
            "  ?a ?ap ?ao." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Building" + buildingId + "> ?p ?o;" +
            "      sch:address ?a." +
            "  ?a ?ap ?ao." +
            "}"
    }


    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * ----------------------------- FLOORS --------------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */


    static getFloorsOfBuilding(buildingId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "CONSTRUCT { " +
            "  ?storey ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Building" + buildingId + "> bot:hasStorey ?storey." +
            "  ?storey ?p ?o." +
            "}"
    }

    static getFloor(floorId){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Storey" + floorId + "> ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Storey" + floorId + "> ?p ?o." +
            "}";
    }


    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * ----------------------------- CORRIDORS -----------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */

    static getCorridorsOfFloor(floorId){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX bot: <https://w3id.org/bot#>" +
            "CONSTRUCT { " +
            "  ?space ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Storey" + floorId + "> bot:hasSpace ?space." +
            "  ?space ?p ?o." +
            "}";
    }


    static getCorridorsOfZone(zoneId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "CONSTRUCT { " +
            "  ?o ?p ?s." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Zone" + zoneId + "> bot:hasSpace ?o." +
            "  ?o ?p ?s." +
            "  filter(regex(str(?o), \"Corridor\"))." +
            "}";
    }

    static getCorridor(corridorId){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Corridor" + corridorId + "> ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Corridor" + corridorId + "> ?p ?o." +
            "}"
    }


    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * ----------------------------- Zones ---------------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */

    static getZonesOfFloor(floorId){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX bot: <https://w3id.org/bot#>" +
            "CONSTRUCT { " +
            "?zone ?p ?s." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Storey" + floorId + "> bot:hasZone ?zone." +
            "  ?zone ?p ?s." +
            "}"
    }

    static getZone(zoneId){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Zone" + zoneId + "> ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Zone" + zoneId + "> ?p ?o." +
            "}"
    }


    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * -------------------------------- ROOMS ------------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */

    static getRoomsOfZone(zoneId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "CONSTRUCT { " +
            "  ?o ?p ?s" +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Zone" + zoneId + "> bot:hasSpace ?o." +
            "  ?o ?p ?s." +
            "  filter(regex(str(?o), \"Room\"))" +
            "}"
    }

    static getRoom(roomId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "PREFIX sch: <http://schema.org/>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Room" + roomId + "> ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Room" + roomId + "> ?p ?o." +
            "}";
    }


    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * ------------------------------ SENSORS ------------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */

    static getSensorsOfCorridor(corridorId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "PREFIX sch: <http://schema.org/>" +
            "PREFIX ssn: <http://www.w3.org/ns/ssn/>" +
            "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "CONSTRUCT { " +
            "  ?sensor ?p ?o." +
            "  ?geo ?geop ?geoo." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Corridor" + corridorId + "> bot:hasElement ?sensor." +
            "  ?sensor a sosa:Sensor." +
            "  ?sensor ?p ?o;" +
            "          sch:GeoCoordinates ?geo." +
            "  ?geo ?geop ?geoo." +
            "}";
    }


    static getSensorsOfRoom(roomId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "PREFIX sch: <http://schema.org/>" +
            "PREFIX ssn: <http://www.w3.org/ns/ssn/>" +
            "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "CONSTRUCT { " +
            "  ?sensor ?p ?o." +
            "  ?geo ?geop ?geoo." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Room" + roomId + "> bot:hasElement ?sensor." +
            "  ?sensor a sosa:Sensor." +
            "  ?sensor ?p ?o;" +
            "          sch:GeoCoordinates ?geo." +
            "  ?geo ?geop ?geoo." +
            "}";
    }

    static getSensor(sensorId){
        return "PREFIX sch: <http://schema.org/>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Sensor" + sensorId + "> ?p ?o." +
            "  ?geo ?geop ?geoo." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Sensor" + sensorId + "> ?p ?o;" +
            "          sch:GeoCoordinates ?geo." +
            "  ?geo ?geop ?geoo." +
            "}"
    }

    static getSensorObservation(sensorId){
        return "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "CONSTRUCT {" +
            "  ?observation ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Sensor" + sensorId + "> sosa:madeObservation ?observation." +
            "  ?observation ?p ?o." +
            "}"
    }

    static putSensorData(sensorId, idObservation, time, data){
        return "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>" +
            "INSERT { " +
            "<http://localhost:3030/genrest/Sensor" + sensorId + "> sosa:madeObservation <http://localhost:3030/genrest/Observation" + idObservation + ">." +
            "  <http://localhost:3030/genrest/Observation" + idObservation + "> a sosa:Observation;" +
            " 	 sosa:madeBySensor <http://localhost:3030/genrest/Sensor" + sensorId + ">;" +
            "    sosa:resultTime \"" + time + "\"^^xsd:dateTime;" +
            "  sosa:hasSimpleResult \"" + data + "\"." +
            "}" +
            "WHERE{}";
    }

    /* ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     * --------------------------- ACTUATORS  ------------------------------
     * ---------------------------------------------------------------------
     * ---------------------------------------------------------------------
     */


    static getActuatorsOfCorridor(corridorId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
            "PREFIX ssn: <http://www.w3.org/ns/ssn/>" +
            "CONSTRUCT { " +
            "  ?actuator ?p ?o." +
            "  ?geo ?geop ?geoo." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Corridor" + corridorId + "> bot:hasElement ?actuator." +
            "  ?actuator a ssn:Actuator." +
            "  ?actuator ?p ?o;" +
            "          geo:hasLocation ?geo." +
            "  ?geo ?geop ?geoo." +
            "}";
    }


    static getActuatorsOfRoom(roomId){
        return "PREFIX bot: <https://w3id.org/bot#>" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
            "PREFIX sch: <http://schema.org/>" +
            "PREFIX ssn: <http://www.w3.org/ns/ssn/>" +
            "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "CONSTRUCT { " +
            "  ?actuator ?p ?o." +
            "  ?geo ?geop ?geoo." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Room" + roomId + "> bot:hasElement ?actuator." +
            "  ?actuator a sosa:Actuator." +
            "  ?actuator ?p ?o;" +
            "          sch:GeoCoordinates ?geo." +
            "  ?geo ?geop ?geoo." +
            "}";
    }

    static getActuator(actuatorId){
        return "PREFIX sch: <http://schema.org/>" +
            "CONSTRUCT { " +
            "  <http://localhost:3030/genrest/Actuator" + actuatorId + "> ?p ?o." +
            "  ?geo ?geop ?geoo." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Actuator" + actuatorId + "> ?p ?o;" +
            "          sch:GeoCoordinates ?geo." +
            "  ?geo ?geop ?geoo." +
            "}"
    }

    static getActuatorActuations(actuatorId){
        return "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "CONSTRUCT {" +
            "  ?observation ?p ?o." +
            "}" +
            "WHERE {" +
            "  <http://localhost:3030/genrest/Actuator" + actuatorId + "> sosa:madeActuation ?actuation." +
            "  ?actuation ?p ?o." +
            "}"
    }

    static putActuatorData(actuatorId, actuationId, time, value){
        return "PREFIX sosa: <http://www.w3.org/ns/sosa/>" +
            "PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>" +
            "INSERT { " +
            "<http://localhost:3030/genrest/Actuator" + actuatorId + "> sosa:madeActuation <http://localhost:3030/genrest/Actuation" + actuationId + ">." +
            "  <http://localhost:3030/genrest/Actuation" + actuationId + "> a sosa:Actuation;" +
            "	 sosa:actuationMadeBy <http://localhost:3030/genrest/Actuator" + actuatorId + ">;" +
            "    sosa:resultTime \"" + time + "\"^^xsd:dateTime;" +
            "  sosa:hasSimpleResult \"" + value + "\"." +
            "}" +
            "WHERE{}";
    }
}

