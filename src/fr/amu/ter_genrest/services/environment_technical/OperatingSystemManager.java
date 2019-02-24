package fr.amu.ter_genrest.services.environment_technical;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;

/**
 * DAO interface to manipulate ORM OperatingSystem data
 * @author Jeremy
 *
 */
@Local
public interface OperatingSystemManager {

	public void addOperatingSystem(OperatingSystem operatingSystem);

	public OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem);

	public void removeOperatingSystem(OperatingSystem operatingSystem);

	public OperatingSystem findById(long id);
	
	public List<OperatingSystem> findAllOperatingSystem();
	
	public OperatingSystem findByName(String name);

	public OperatingSystem findByPathFolder(String nameFolder);
	
}
