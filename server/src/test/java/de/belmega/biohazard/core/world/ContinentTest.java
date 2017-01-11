package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class ContinentTest {

    @Test
    public void testThat_whenContinentTicks_allCountriesTick() throws Exception {
        //arrange
        Country country1 = Mockito.mock(Country.class);
        when(country1.getState().getName()).thenReturn("bar");

        Country country2 = Mockito.mock(Country.class);
        when(country2.getState().getName()).thenReturn("baz");

        Continent continent = new Continent(new ContinentState("foo"));
        continent.add(country1, country2);

        //act
        continent.tick();

        //assert
        verify(country1, times(1)).tick();
        verify(country2, times(1)).tick();
    }
}
