package de.belmega.biohazard.core.country;

import de.belmega.biohazard.server.persistence.state.CountryState;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TravelRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "routes", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Size(max = 2)
    private Set<CountryState> countryStates = new HashSet<>();


    /**
     * Number between 0 and 1. Describes which part of each countries poplation travels to the other each tick.
     */
    private double travelingPopulationFactorPerTick;

    private TravelRouteType type;

    /**
     * Constructor only for JPA. Use TravelRoute.create() instead.
     */

    public TravelRoute() {
    }

    private TravelRoute(CountryState country1, CountryState country2, TravelRouteType type) {
        this.countryStates.add(country1);
        this.countryStates.add(country2);
        this.type = type;
    }

    public static TravelRoute create(CountryState country2State, CountryState country1State, TravelRouteType land) {
        TravelRoute travelRoute = new TravelRoute(country2State, country1State, land);
        country2State.addRoute(travelRoute);
        country1State.addRoute(travelRoute);
        return travelRoute;
    }

    public double getTravelingPopulationFactorPerTick() {
        return travelingPopulationFactorPerTick;
    }

    public void setTravelingPopulationFactorPerTick(double travelingPopulationPercentagePerTick) {
        this.travelingPopulationFactorPerTick = travelingPopulationPercentagePerTick;
    }

    public long getTravelersPerTick() {
        long populations = 0;
        for (CountryState country : this.countryStates)
            populations += country.getPopulation();
        return Math.round(
                travelingPopulationFactorPerTick * populations);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Assuming the set of countries consists of exact two countries,
     * returns the country different from the given one.
     *
     * @param sourceCountry the country to start from
     * @return the country to travel to
     */
    public CountryState getTargetCountry(CountryState sourceCountry) {
        for (CountryState country : this.countryStates)
            if (!country.equals(sourceCountry))
                return country;

        throw new IllegalStateException("No different country found in " + countryStates);
    }
}
