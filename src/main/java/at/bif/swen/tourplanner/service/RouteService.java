package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.dto.RouteInfo;
import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.openRouteService.GeoCoord;
import at.bif.swen.tourplanner.openRouteService.OpenRouteServiceAgent;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private OpenRouteServiceAgent openRouteServiceAgent;

    private GeoCoord start;
    private GeoCoord end;



    public void getTourInformation(TourItem tourItem) {
        try{
            this.setTourCoords(tourItem);
            JsonNode routJSON = getRouteJSON(tourItem);
            RouteInfo routeInfo = this.extractRouteInfo(routJSON);
            tourItem.setDistance(routeInfo.getDistance());
            tourItem.setEstimatedDuration(routeInfo.getDuration());


        }catch(Exception e){

        }
    }

    public void setTourCoords(TourItem touritem){
        this.start = openRouteServiceAgent.geoCode(touritem.getFrom());
        this.end = openRouteServiceAgent.geoCode(touritem.getTo());


    }


    private JsonNode getRouteJSON (TourItem tourItem) {
        try{
            this.setTourCoords(tourItem);
            JsonNode routeJSON = openRouteServiceAgent.directions(tourItem.getTransportType(),this.start,this.end);
            return routeJSON;

        }catch(Exception e){
            return null;
        }
    }

    public RouteInfo extractRouteInfo(JsonNode json) {
        if (json == null || !json.has("routes")) return null;

        JsonNode summary = json.path("routes").get(0).path("summary");
        double distance = summary.path("distance").asDouble();
        double duration = summary.path("duration").asDouble();

        return new RouteInfo(distance, duration);
    }


}
