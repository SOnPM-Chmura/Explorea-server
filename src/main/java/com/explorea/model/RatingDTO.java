package com.explorea.model;

import java.util.Objects;

public class RatingDTO {

    private Integer routeId;

    private Integer rating;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
                "routeId=" + routeId +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingDTO ratingDTO = (RatingDTO) o;
        return Objects.equals(routeId, ratingDTO.routeId) &&
                Objects.equals(rating, ratingDTO.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, rating);
    }
}
