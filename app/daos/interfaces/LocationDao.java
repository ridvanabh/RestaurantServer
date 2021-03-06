package daos.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.PagedList;
import models.Country;
import models.Location;

import java.util.List;

public interface LocationDao {

    //Create methods

    void createLocation(Location newLocation);

    void updateLocation(Location location);

    List<Location> getAllLocOfCountry(String countryName);

    Location getLocationById(Long id);

    PagedList<Location> getFilteredLocations(JsonNode json);

    int getNumberLocations();

    //Read methods
    Location getById(Long id);

    Location getLocationByName(String name);

    Boolean checkIfExists(String name);

    List<Location> getLocationsOfCountry(Country country);

    Location checkIfExistsThenReturn(Location location);

    void deleteLocation(Location location);

    //Update methods

    //Delete methods
}
