package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Zone;

@Local
public interface ZoneManager {
	
	public Zone updateZone(Zone zone);
	
	public Zone findById (Long id);

	public List<Zone> findAllZone();
	
}