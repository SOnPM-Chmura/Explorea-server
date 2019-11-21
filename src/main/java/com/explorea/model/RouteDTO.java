package com.explorea.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class RouteDTO {

    @Id
    private Integer id;

    private String codedRoute;

    private Double avgRating;

    private Integer lengthByFoot;

    private Integer lengthByBike;

    private Integer timeByFoot;

    private Integer timeByBike;

    private String city;

    public Integer getLengthByFoot() {
        return lengthByFoot;
    }

    public void setLengthByFoot(Integer lengthByFoot) {
        this.lengthByFoot = lengthByFoot;
    }

    public Integer getLengthByBike() {
        return lengthByBike;
    }

    public void setLengthByBike(Integer lengthByBike) {
        this.lengthByBike = lengthByBike;
    }

    public Integer getTimeByFoot() {
        return timeByFoot;
    }

    public void setTimeByFoot(Integer timeByFoot) {
        this.timeByFoot = timeByFoot;
    }

    public Integer getTimeByBike() {
        return timeByBike;
    }

    public void setTimeByBike(Integer timeByBike) {
        this.timeByBike = timeByBike;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodedRoute() {
        return codedRoute;
    }

    public void setCodedRoute(String codedRoute) {
        this.codedRoute = codedRoute;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "RouteDTO{" +
                "id=" + id +
                ", codedRoute='" + codedRoute + '\'' +
                ", avgRating=" + avgRating +
                ", lengthByFoot=" + lengthByFoot +
                ", lengthByBike=" + lengthByBike +
                ", timeByFoot=" + timeByFoot +
                ", timeByBike=" + timeByBike +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteDTO routeDTO = (RouteDTO) o;
        return Objects.equals(id, routeDTO.id) &&
                Objects.equals(codedRoute, routeDTO.codedRoute) &&
                Objects.equals(avgRating, routeDTO.avgRating) &&
                Objects.equals(lengthByFoot, routeDTO.lengthByFoot) &&
                Objects.equals(lengthByBike, routeDTO.lengthByBike) &&
                Objects.equals(timeByFoot, routeDTO.timeByFoot) &&
                Objects.equals(timeByBike, routeDTO.timeByBike) &&
                Objects.equals(city, routeDTO.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codedRoute, avgRating, lengthByFoot, lengthByBike, timeByFoot, timeByBike, city);
    }
}
