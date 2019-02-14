package fr.amu.terGENREST.services.environmentTechnical;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.environmentTechnical.Language;
import io.codearte.jfairy.producer.person.Person;

@Stateless
public class LanguagesManagerImpl implements LanguagesManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public void addLanguage(Language language) {
		em.persist(language);
	}

	@Override
	public Language updateLanguage(Language language) {
		return em.merge(language);
	}

	@Override
	public void removeLanguage(Language language) {
		em.remove(em.contains(language) ? language : em.merge(language));
	}

	@Override
	public List<Language> findAllLanguages() {
		TypedQuery<Language> q = em.createNamedQuery("Language.findAllLanguages", Language.class);
	
		return q.getResultList();
	}

	@Override
	public Language findById(long id) {
		return em.find(Language.class, id);
	}

	@Override
	public Language findByName(String name) {
		TypedQuery<Language> q = em.createNamedQuery("Language.findByName", Language.class)
				.setParameter("name", name);
		
		try {
			return q.getSingleResult();	
		} catch (NoResultException e) {
			return null;
		}
	}

}
