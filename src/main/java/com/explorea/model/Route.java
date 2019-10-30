package com.explorea.model;

import org.springframework.data.annotation.Id;

public class Route {
    //@Id
    private Integer id;

    private String placesList;

    private Double length;

    private Double averageRating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlacesList() {
        return placesList;
    }

    public void setPlacesList(String placesList) {
        this.placesList = placesList;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", placesList='" + placesList + '\'' +
                ", length=" + length +
                ", averageRating=" + averageRating +
                '}';
    }
}
