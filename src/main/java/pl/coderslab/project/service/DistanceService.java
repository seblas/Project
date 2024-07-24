package pl.coderslab.project.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    private final GeoApiContext geoApiContext;

    public DistanceService(@Value("${google.maps.api.key}") String apiKey) {
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public long calculateDistance(String origin, String destination) {
        try {
            DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(geoApiContext, new String[]{origin}, new String[]{destination}).await();
            return result.rows[0].elements[0].distance.inMeters/1000;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating distance", e);
        }
    }
}
