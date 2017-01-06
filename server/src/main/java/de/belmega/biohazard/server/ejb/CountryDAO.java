package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.server.persistence.state.CountryState;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CountryDAO {

    @PersistenceContext
    EntityManager em;

    public CountryState findCountry(long countryId) {
        return em.find(CountryState.class, countryId);
    }
}
