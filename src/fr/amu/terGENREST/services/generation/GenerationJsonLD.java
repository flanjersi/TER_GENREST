package fr.amu.terGENREST.services.generation;

import javax.ejb.Local;
import javax.json.JsonObject;

import fr.amu.terGENREST.entities.project.Project;

@Local
public interface GenerationJsonLD {

	public JsonObject generateJsonLDDataFile(Project project);
	
}
