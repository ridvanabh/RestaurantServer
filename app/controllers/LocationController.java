package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import daos.implementations.CountryDaoImpl;
import daos.implementations.LocationDaoImpl;
import daos.interfaces.CountryDao;
import daos.interfaces.LocationDao;
import io.ebean.PagedList;
import models.Location;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class LocationController extends Controller {

    LocationDao locDao = new LocationDaoImpl();
    CountryDao coutDao = new CountryDaoImpl();

    //POST

    public Result getFilteredLocations  (){
        JsonNode json = request().body().asJson();

        if (json == null)
            return badRequest("Json is null");

        try {
            PagedList result = locDao.getFilteredLocations(json);

            ObjectMapper mapper = new ObjectMapper();

            ObjectNode returnNode = mapper.createObjectNode();
            returnNode.put("numberOfPages", result.getTotalPageCount());

            ArrayNode locationsNode = mapper.createArrayNode();

            List<Location> locations = result.getList();

            for (Location location:locations
                 ) {
                JsonNode node = mapper.createObjectNode();
                ((ObjectNode) node).put("id", location.id);
                ((ObjectNode) node).put("name", location.getName());
                locationsNode.add(node);
            }

            returnNode.putArray("locations").addAll(locationsNode);

            return ok((new ObjectMapper()).writeValueAsString(returnNode));


        } catch (Exception e) {
            return badRequest(e.getMessage());
        }
    }

    public Result addLocation(){

        JsonNode json = request().body().asJson();

        if (json == null)
            return badRequest("Json is null");

        try {
            Location newLocation = new Location(json.get("name").asText(), coutDao.getCountryByName(json.get("country").asText()));
            locDao.createLocation(newLocation);

            return ok(getLocJson(newLocation));
        }
        catch (Exception e) {
            return badRequest("Location already exists");
        }

    }

    public Result editLocation(){

        JsonNode json = request().body().asJson();

        if (json == null)
            return badRequest("Json is null");

        try {
            Location location= locDao.getById(json.get("id").asLong());
            location.setName(json.get("name").asText());

            locDao.updateLocation(location);

            return ok(getLocJson(location));
        }
        catch (Exception e) {
            return badRequest(e.getMessage());
        }

    }

    public Result deleteLocation () {
        JsonNode json = request().body().asJson();

        if(json==null)
            return badRequest("Json is null");

        try{
            Location location= locDao.getById(json.get("id").asLong());

            locDao.deleteLocation(location);

            return ok();
        }catch (Exception e){
            return badRequest(e.getMessage());
        }

    }

    public Result getLocationDetails(){
        JsonNode json = request().body().asJson();

        if(json==null)
            return badRequest("Json is null");

        try{

            return ok(getLocJson(locDao.getById(json.get("id").asLong())));

        }catch (Exception e){
            return badRequest(e.getMessage());
        }
    }

    private JsonNode getLocJson(Location newLocation){
        JsonNode rootNode = (new ObjectMapper()).createObjectNode();

        ((ObjectNode) rootNode).put("id", newLocation.id);
        ((ObjectNode) rootNode).put("name", newLocation.getName());
        return rootNode;
    }
}
