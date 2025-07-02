package at.bif.swen.tourplanner.openRouteService;

import at.bif.swen.tourplanner.model.TransportType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class OpenRouteServiceAgent {

    @Value("${openrouteservice.api-key}")
    private String apiKey;

    public OpenRouteServiceAgent() {

    }

    public GeoCoord geoCode(String postalAddress) {
        try {
            postalAddress = URLEncoder.encode(postalAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported characters in postal address: " + e.getMessage());
            return null;
        }
        String url = String.format("https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s", apiKey, postalAddress);

        try (HttpClient client = HttpClient.newHttpClient();) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri( URI.create(url) )
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );
            if ( response.statusCode() == 200 ) {


                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.body());


                try {
                    var coords = root.get("features").get(0).get("geometry").get("coordinates");
                    return new GeoCoord(coords.get(0).asDouble(), coords.get(1).asDouble());
                }
                catch (Exception e) {
                    System.err.println("Failed to parse REST response: " + root.toPrettyString());
                    return null;
                }
            }
            else  {
                System.err.println( "Failed to process request: " + response.body() );
                return null;
            }

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public JsonNode directions(TransportType transportType, GeoCoord start, GeoCoord end) {

        var formatter = NumberFormat.getNumberInstance(Locale.UK);
        formatter.setMaximumFractionDigits(6);

        String url = String.format("https://api.openrouteservice.org/v2/directions/%s?api_key=%s&start=%s,%s&end=%s,%s",
                transportType.toString(), apiKey, formatter.format(start.lat()), formatter.format(start.lon()), formatter.format(end.lat()), formatter.format(end.lon()));


        try (HttpClient client = HttpClient.newHttpClient();) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri( URI.create(url) )
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );
            if ( response.statusCode() == 200 ) {

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.body());
                return root;
            }
            else  {
                System.err.println( "Failed to process request: " + response.body() );
                return null;
            }

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }



}
