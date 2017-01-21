package de.belmega.biohazard.core.country;

import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.WorldState;

import javax.persistence.*;

@Entity
public class TravelRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private CountryState country1;

    @Transient
    private CountryState country2;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false)
    private WorldState world;

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

    ;

    private TravelRoute(CountryState country1, CountryState country2, TravelRouteType type) {
        this.country1 = country1;
        this.country2 = country2;
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
        return Math.round(
                travelingPopulationFactorPerTick *
                        (country1.getPopulation() + country2.getPopulation()));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorldState getWorld() {
        return world;
    }

    public void setWorld(WorldState world) {
        this.world = world;
    }

    public CountryState getTargetCountry(CountryState sourceCountry) {
        if (country1.equals(sourceCountry)) return country2;
        else if (country2.equals(sourceCountry)) return country1;
        else throw new IllegalArgumentException("Not part of this route: " + sourceCountry);
    }
}
