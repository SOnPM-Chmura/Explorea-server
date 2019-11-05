package com.explorea.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Route {
    @Id
    private Integer id;

    private String codedRoute;

    private Double averageRating;

    private Integer lengthByFoot;

    private Integer lengthByBike;

    private Integer timeByFoot;

    private Integer timeByBike;

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
                ", codedRoute='" + codedRoute + '\'' +
                ", averageRating=" + averageRating +
                ", lengthByFoot=" + lengthByFoot +
                ", lengthByBike=" + lengthByBike +
                ", timeByFoot=" + timeByFoot +
                ", timeByBike=" + timeByBike +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) &&
                Objects.equals(codedRoute, route.codedRoute) &&
                Objects.equals(averageRating, route.averageRating) &&
                Objects.equals(lengthByFoot, route.lengthByFoot) &&
                Objects.equals(lengthByBike, route.lengthByBike) &&
                Objects.equals(timeByFoot, route.timeByFoot) &&
                Objects.equals(timeByBike, route.timeByBike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codedRoute, averageRating, lengthByFoot, lengthByBike, timeByFoot, timeByBike);
    }
}
