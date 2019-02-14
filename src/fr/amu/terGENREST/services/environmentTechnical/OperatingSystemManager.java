package fr.amu.terGENREST.services.environmentTechnical;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.environmentTechnical.OperatingSystem;

/**
 * DAO interface to manipulate ORM OperatingSystem data
 * @author J�r�my
 *
 */
@Local
public interface OperatingSystemManager {

	public void addOperatingSystem(OperatingSystem operatingSystem);

	public OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem);

	public void removeOperatingSystem(OperatingSystem operatingSystem);

	
	public OperatingSystem findById(long id);
	
	public List<OperatingSystem> findAllOperatingSystem();
}