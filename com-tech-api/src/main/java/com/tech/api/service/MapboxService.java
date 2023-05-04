package com.tech.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mapbox.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapboxService {
    private static final double EARTH_RADIUS = 6371; // km

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mapbox.accessToken}")
    private String accessToken;

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }


    public Point getPoint(String address){
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/{location}.json?access_token={accessToken}";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("location", address);
        uriVariables.put("accessToken", accessToken);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, uriVariables);
        String responseBody = responseEntity.getBody();
        if (responseBody == null){
            return null;
        }
        JsonElement jsonElement = JsonParser.parseString(responseBody);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray featuresArray = jsonObject.getAsJsonArray("features");

        if (featuresArray.size() > 0) {
            JsonObject firstFeature = featuresArray.get(0).getAsJsonObject();
            JsonArray coordinatesArray = firstFeature.getAsJsonArray("center");
            double longitude = coordinatesArray.get(0).getAsDouble();
            double latitude = coordinatesArray.get(1).getAsDouble();
            return Point.fromLngLat(longitude, latitude);
        } else {
            System.out.println("No location found.");
        }
        return null;
    }
}
