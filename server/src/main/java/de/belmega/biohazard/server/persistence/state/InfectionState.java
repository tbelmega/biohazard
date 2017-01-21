package de.belmega.biohazard.server.persistence.state;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * An InfectionState counts the number of people that are infected with a specific disease in a specific country.
 */
@Entity
public class InfectionState {

    @ManyToOne
    @JoinColumn(name = "disease", nullable = false)
    private DiseaseState disease;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "country", nullable = false)
    private CountryState country;

    /**
     * Amount of infected people.
     */
    private Long amount;

    private InfectionState(CountryState countryState, DiseaseState disease, Long infectedPeople) {
        this.country = countryState;
        this.disease = disease;
        this.amount = infectedPeople;
    }

    public InfectionState() {
    }

    /**
     * Create an infection state entity with the given parameters and add it to the country as well.
     */
    public static InfectionState create(CountryState country, DiseaseState disease, long amount) {
        InfectionState infectionState = new InfectionState(country, disease, amount);
        country.add(infectionState);
        return infectionState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return disease.getName();
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
                .append("Disease", getDiseaseName())
                .append("Country", country.getName())
                .append("InfectionState count", amount)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfectionState infectionState = (InfectionState) o;

        //TODO should the amount be a criterium for equality?
        return new EqualsBuilder()
                .append(this.getDiseaseName(), infectionState.getDiseaseName())
                .append(this.country, infectionState.getCountry())
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getDiseaseName())
                .append(this.country)
                .build();
    }

    public DiseaseState getDisease() {
        return disease;
    }

    public void increaseAmount(long addedAmount) {
        this.amount += addedAmount;
    }
}
