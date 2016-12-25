package de.belmega.biohazard.server.persistence;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author tbelmega on 17.12.2016.
 */
@Entity
public class InfectionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private CountryState country;

    private String diseaseName;

    private Long amount;

    public InfectionState(CountryState countryState, String diseaseName, Long infectedPeople) {
        this.country = countryState;
        this.diseaseName = diseaseName;
        this.amount = infectedPeople;
    }

    public InfectionState() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public CountryState getCountry() {
        return country;
    }

    public void setCountry(CountryState country) {
        this.country = country;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Disease", diseaseName)
                .append("Country", country.getName())
                .append("Infection count", amount)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfectionState infection = (InfectionState) o;

        //TODO should the amount be a criterium for equality?
        return new EqualsBuilder()
                .append(this.diseaseName, infection.getDiseaseName())
                .append(this.country, infection.getCountry())
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.diseaseName)
                .append(this.country)
                .build();
    }
}
