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


	public OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem);

	public OperatingSystem findById(long id);

}
