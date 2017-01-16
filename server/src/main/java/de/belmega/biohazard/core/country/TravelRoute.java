package de.belmega.biohazard.core.country;

import de.belmega.biohazard.server.persistence.state.CountryState;

/**
 * @author tbelmega on 15.01.2017.
 */
public class TravelRoute {
    private final CountryState country1;
    private final CountryState country2;
    private final TravelRouteType type;

    /**
     * Number between 0 and 1. Describes which part of each countries poplation travels to the other each tick.
     */
    private double travelingPopulationFactorPerTick;

    private TravelRoute(CountryState country1, CountryState country2, TravelRouteType type) {
        this.country1 = country1;
        this.country2 = country2;
        this.type = type;
    }

    public static TravelRoute create(CountryState germanyState, CountryState polandState, TravelRouteType land) {
        TravelRoute travelRoute = new TravelRoute(germanyState, polandState, land);

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
}
