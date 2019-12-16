package com.explorea;

import com.explorea.model.SimpleDirectionsRoute;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class ApiDirectionsDAO {

    private final String API_KEY = "";

    public SimpleDirectionsRoute getSimpleDirectionsRoute(String encodedRoute) {
        encodedRoute = PolyUtilSimple.hexDecode(encodedRoute);

        String urlFoot = createURL(PolyUtilSimple.decode(encodedRoute).toArray(new SimpleDirectionsRoute.LatLng[0]),
                By.Foot,
                API_KEY);
        String urlBike = createURL(PolyUtilSimple.decode(encodedRoute).toArray(new SimpleDirectionsRoute.LatLng[0]),
                By.Bike,
                API_KEY);

        JSONObject directionsFoot = getDirectionsJSON(urlFoot);
        JSONObject directionsBike = getDirectionsJSON(urlBike);

        SimpleDirectionsRoute simpleDirectionsRoute = null;
        try {
            simpleDirectionsRoute = parseSimpleDirectionsRoute(encodedRoute, directionsFoot, directionsBike);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return simpleDirectionsRoute;
    }

    private String createURL(SimpleDirectionsRoute.LatLng[] route, By what, String apiKey) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?key=" + apiKey);
        try {
            try {
                url.append("&origin=").append(URLEncoder.encode(route[0].lat + "," + route[0].lng,
                        StandardCharsets.UTF_8.name()));
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
            if (route.length > 2) {
                url.append("&waypoints=");
                for (int i = 1; i < route.length - 1; i++) {
                    url.append(URLEncoder.encode("via:" + route[i].lat + "," + route[i].lng + "|",
                            StandardCharsets.UTF_8.name()));
                }
                url.delete(url.length()-3, url.length());
            } else if (route.length == 1) return null;
            url.append("&destination=")
                    .append(URLEncoder.encode(route[route.length - 1].lat + "," + route[route.length-1].lng,
                            StandardCharsets.UTF_8.name()));
            if (what == By.Foot) {
                url.append("&mode=walking");
            } else
            if (what == By.Bike) {
                url.append("&mode=bicycling");
            } else {
                return null;
            }
            return url.toString();

        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private JSONObject getDirectionsJSON(String url) {

        RestTemplate restTemplate = new RestTemplate();

        String response = null;
        try {
            response = restTemplate.getForObject(new URI(url), String.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        JSONObject jsonResponse = null;

        try {
            jsonResponse = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private SimpleDirectionsRoute parseSimpleDirectionsRoute(String encodedRoute, JSONObject directionsFoot, JSONObject directionsBike) throws JSONException {
        long queryTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();

        if(directionsFoot == null || directionsBike == null) {
            return null;
        }

        double bound_ne_latf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("northeast")
                .getDouble("lat");

        double bound_ne_lngf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("northeast")
                .getDouble("lng");

        double bound_sw_latf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("southwest")
                .getDouble("lat");

        double bound_sw_lngf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("southwest")
                .getDouble("lng");


        double bound_ne_latb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("northeast")
                .getDouble("lat");

        double bound_ne_lngb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("northeast")
                .getDouble("lng");

        double bound_sw_latb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("southwest")
                .getDouble("lat");

        double bound_sw_lngb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("bounds")
                .getJSONObject("southwest")
                .getDouble("lng");

        SimpleDirectionsRoute.LatLngBounds bounds = new SimpleDirectionsRoute.LatLngBounds(
                new SimpleDirectionsRoute.LatLng(Math.min(bound_sw_latb, bound_sw_latf),
                        Math.min(bound_sw_lngb, bound_sw_lngf)),
                new SimpleDirectionsRoute.LatLng(Math.max(bound_ne_latb, bound_ne_latf),
                        Math.max(bound_ne_lngb, bound_ne_lngf))
        );

        int disf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("distance")
                .getInt("value");

        int timf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("duration")
                .getInt("value") / 60;

        int disb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("distance")
                .getInt("value");

        int timb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("duration")
                .getInt("value") / 60;

        String dirf = directionsFoot.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("overview_polyline")
                .getString("points");

        dirf = PolyUtilSimple.hexEncode(dirf);

        String dirb = directionsBike.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("overview_polyline")
                .getString("points");

        dirb = PolyUtilSimple.hexEncode(dirb);

        encodedRoute = PolyUtilSimple.hexEncode(encodedRoute);

        return new SimpleDirectionsRoute(
                encodedRoute,
                queryTime,
                dirf,
                dirb,
                disf,
                disb,
                timf,
                timb,
                bounds
        );
    }

    private enum By {
        Foot, Bike
    }
}
