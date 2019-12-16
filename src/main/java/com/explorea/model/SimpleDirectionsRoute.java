package com.explorea.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SimpleDirectionsRoute {

    String encodedRoute;
    long queryTime;
    String encodedDirectionsFoot;
    String encodedDirectionsBike;
    int distanceFoot;
    int distanceBike;
    int timeFoot;
    int timeBike;
    LatLngBounds bounds;
    String city;

    public SimpleDirectionsRoute(String encodedRoute,
                                 long queryTime,
                                 String encodedDirectionsFoot,
                                 String encodedDirectionsBike,
                                 int distanceFoot,
                                 int distanceBike,
                                 int timeFoot,
                                 int timeBike,
                                 LatLngBounds bounds,
                                 String city) {
        this.encodedRoute = encodedRoute;
        this.queryTime = queryTime;
        this.encodedDirectionsFoot = encodedDirectionsFoot;
        this.encodedDirectionsBike = encodedDirectionsBike;
        this.distanceFoot = distanceFoot;
        this.distanceBike = distanceBike;
        this.timeFoot = timeFoot;
        this.timeBike = timeBike;
        this.bounds = bounds;
        this.city = city;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"encodedRoute\": \"" + encodedRoute + "\",\n" +
                "\"queryTime\": " + queryTime + ",\n" +
                "\"encodedDirectionsFoot\": \"" + encodedDirectionsFoot + "\",\n" +
                "\"encodedDirectionsBike\": \"" + encodedDirectionsBike + "\",\n" +
                "\"distanceFoot\": " + distanceFoot + ",\n" +
                "\"distanceBike\": " + distanceBike + ",\n" +
                "\"timeFoot\": " + timeFoot + ",\n" +
                "\"timeBike\": " + timeBike + ",\n" +
                "\"bounds\": " + bounds.toString() + ",\n" +
                "\"city\": \"" + city + "\"}";

    }

    @JsonSerialize
    public static class LatLngBounds {
        LatLng northEast;
        LatLng southWest;

        public LatLng getNorthEast() {
            return northEast;
        }

        public void setNorthEast(LatLng northEast) {
            this.northEast = northEast;
        }

        public LatLng getSouthWest() {
            return southWest;
        }

        public void setSouthWest(LatLng southWest) {
            this.southWest = southWest;
        }

        public LatLngBounds(LatLng southWest, LatLng northEast) {
            this.northEast = northEast;
            this.southWest = southWest;
        }



        @Override
        public String toString() {
            return "{\n" +
                    "\"northEast\": " + northEast.toString() + ",\n" +
                    "\"southWest\": " + southWest.toString() + "\n}";
        }
    }


    public static class LatLng {
        public double lat;
        public double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public LatLng(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "{\n" +
                    "\"lat\": " + lat + ",\n" +
                    "\"lng\": " + lng + "\n}";
        }
    }

    public String getEncodedRoute() {
        return encodedRoute;
    }

    public void setEncodedRoute(String encodedRoute) {
        this.encodedRoute = encodedRoute;
    }

    public long getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(long queryTime) {
        this.queryTime = queryTime;
    }

    public String getEncodedDirectionsFoot() {
        return encodedDirectionsFoot;
    }

    public void setEncodedDirectionsFoot(String encodedDirectionsFoot) {
        this.encodedDirectionsFoot = encodedDirectionsFoot;
    }

    public String getEncodedDirectionsBike() {
        return encodedDirectionsBike;
    }

    public void setEncodedDirectionsBike(String encodedDirectionsBike) {
        this.encodedDirectionsBike = encodedDirectionsBike;
    }

    public int getDistanceFoot() {
        return distanceFoot;
    }

    public void setDistanceFoot(int distanceFoot) {
        this.distanceFoot = distanceFoot;
    }

    public int getDistanceBike() {
        return distanceBike;
    }

    public void setDistanceBike(int distanceBike) {
        this.distanceBike = distanceBike;
    }

    public int getTimeFoot() {
        return timeFoot;
    }

    public void setTimeFoot(int timeFoot) {
        this.timeFoot = timeFoot;
    }

    public int getTimeBike() {
        return timeBike;
    }

    public void setTimeBike(int timeBike) {
        this.timeBike = timeBike;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public void setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }
}