package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ContinentTest {

    @Test
    public void testThat_tickForContinentCallsTickForCountries() throws Exception {
        //arrange
        Continent continent = new Continent();
        Country country1 = Mockito.mock(Country.class);
        Country country2 = Mockito.mock(Country.class);
        Country country3 = Mockito.mock(Country.class);
        continent.add(country1, country2, country3);

        //act
        continent.tick();

        //assert
        verify(country1, times(1)).tick();
        verify(country2, times(1)).tick();
        verify(country3, times(1)).tick();
    }
}
