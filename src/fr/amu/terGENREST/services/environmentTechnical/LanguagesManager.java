package fr.amu.terGENREST.services.environmentTechnical;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.environmentTechnical.Language;

/**
 * DAO interface to manipulate ORM Languages data
 * @author Jérémy
 *
 */
@Local
public interface LanguagesManager {
	
	public void addLanguage(Language language);
	
	public Language updateLanguage(Language language);
	
	public void removeLanguage(Language language);
	
	public void findAllLanguages();
}
