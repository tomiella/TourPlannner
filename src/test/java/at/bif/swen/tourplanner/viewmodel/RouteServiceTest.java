package at.bif.swen.tourplanner.viewmodel;


import at.bif.swen.tourplanner.dto.RouteInfo;
import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TransportType;
import at.bif.swen.tourplanner.openRouteService.GeoCoord;
import at.bif.swen.tourplanner.openRouteService.OpenRouteServiceAgent;
import at.bif.swen.tourplanner.service.RouteService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteServiceTest {

    @Mock
    private OpenRouteServiceAgent openRouteServiceAgent;

    @InjectMocks
    private RouteService routeService;

    private TourItem tourItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tourItem = new TourItem();
        tourItem.setFrom("Vienna");
        tourItem.setTo("Graz");
        tourItem.setTransportType(TransportType.CAR);
    }

    @Test
    void testSetTourCoords() {
        GeoCoord start = new GeoCoord(48.2082, 16.3738); // Vienna
        GeoCoord end = new GeoCoord(47.0707, 15.4395); // Graz

        when(openRouteServiceAgent.geoCode("Vienna")).thenReturn(start);
        when(openRouteServiceAgent.geoCode("Graz")).thenReturn(end);

        routeService.setTourCoords(tourItem);

        // Internal state is private, but you can verify the method was called
        verify(openRouteServiceAgent).geoCode("Vienna");
        verify(openRouteServiceAgent).geoCode("Graz");
    }

    @Test
    void testExtractRouteInfo() {
        // Mock JSON structure for route info
        ObjectNode segmentNode = JsonNodeFactory.instance.objectNode();
        segmentNode.put("distance", 20000); // in meters
        segmentNode.put("duration", 1800);  // in seconds

        ObjectNode propertiesNode = JsonNodeFactory.instance.objectNode();
        propertiesNode.set("segments", JsonNodeFactory.instance.arrayNode().add(segmentNode));

        ObjectNode featureNode = JsonNodeFactory.instance.objectNode();
        featureNode.set("properties", propertiesNode);

        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.set("features", JsonNodeFactory.instance.arrayNode().add(featureNode));

        RouteInfo info = routeService.extractRouteInfo(root);

        assertEquals(20000, info.getDistance());
        assertEquals(1800, info.getDuration());
    }

    @Test
    void testGetTourInformation() {
        GeoCoord start = new GeoCoord(48.2082, 16.3738);
        GeoCoord end = new GeoCoord(47.0707, 15.4395);

        when(openRouteServiceAgent.geoCode("Vienna")).thenReturn(start);
        when(openRouteServiceAgent.geoCode("Graz")).thenReturn(end);

        // Fake route JSON
        ObjectNode segmentNode = JsonNodeFactory.instance.objectNode();
        segmentNode.put("distance", 30000);
        segmentNode.put("duration", 3600);

        ObjectNode propertiesNode = JsonNodeFactory.instance.objectNode();
        propertiesNode.set("segments", JsonNodeFactory.instance.arrayNode().add(segmentNode));

        ObjectNode featureNode = JsonNodeFactory.instance.objectNode();
        featureNode.set("properties", propertiesNode);

        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.set("features", JsonNodeFactory.instance.arrayNode().add(featureNode));

        when(openRouteServiceAgent.directions(TransportType.CAR, start, end)).thenReturn(root);

        routeService.getTourInformation(tourItem);

        assertEquals(30000, tourItem.getDistance());
        assertEquals(3600, tourItem.getEstimatedDuration());
    }
}
