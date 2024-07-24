package pl.coderslab.project.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeocodingService {

    private final GeoApiContext context;

    public GeocodingService(@Value("${google.maps.api.key}") String apiKey) {
        this.context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public GeocodingResult[] getCoordinates(String address) throws ApiException, InterruptedException, IOException {
        return GeocodingApi.geocode(context, address).await();
    }

    public static void main(String[] args) {
        String apiKey = "AIzaSyD0u91aZXxT9HKZoxGFhQMxXZ_TWkuqrNU";
        GeocodingService geocodingService = new GeocodingService(apiKey);

        try {
            GeocodingResult[] results = geocodingService.getCoordinates("05-091 Ząbki, Legionów 9");
            if (results.length > 0) {
                System.out.println("Latitude: " + results[0].geometry.location.lat);
                System.out.println("Longitude: " + results[0].geometry.location.lng);
            }
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
