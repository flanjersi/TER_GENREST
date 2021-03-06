package fr.amu.ter_genrest.services.environment_technical;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.environment_technical.Language;

/**
 * DAO interface to manipulate ORM Languages data
 * @author Jeremy
 *
 */
@Local
public interface LanguagesManager {
	
	public void addLanguage(Language language);
	
	public Language updateLanguage(Language language);
	
	public void removeLanguage(Language language);
	
	public Language findById(long id);
	
	public List<Language> findAllLanguages();
	
	public Language findByName(String name);
	
}
