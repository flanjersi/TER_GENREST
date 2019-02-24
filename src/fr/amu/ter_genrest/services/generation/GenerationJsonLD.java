package fr.amu.ter_genrest.services.generation;

import javax.ejb.Local;
import javax.json.JsonObject;

import fr.amu.ter_genrest.entities.project.Project;

@Local
public interface GenerationJsonLD {

	public JsonObject generateJsonLDDataFile(Project project);
	
}
