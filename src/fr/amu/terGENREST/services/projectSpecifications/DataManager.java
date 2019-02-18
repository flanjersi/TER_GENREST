package fr.amu.terGENREST.services.projectSpecifications;

import fr.amu.terGENREST.entities.projectSpecifications.Data;

/**
 * DAO for data add, update, remove, and find By id
 * @author tsila
 *
 */

public interface DataManager {


	@Deprecated
	public void addData(Data data);
	
	public Data updateData(Data data);

	@Deprecated
	public void removeData(Data data);

	public Data findData(Long id);
}
