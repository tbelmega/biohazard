package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.persistence.dao.CountryDAO;
import de.belmega.biohazard.server.persistence.state.CountryState;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CountryDetailsBean {
    private long countryId;
    private CountryState country;

    @Inject
    private CountryDAO countryDAO;

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public CountryState getCountry() {
        return country;
    }

    public void setCountry(CountryState country) {
        this.country = country;
    }

    public void loadCountry() {
        country = countryDAO.findCountry(countryId);
    }
}
