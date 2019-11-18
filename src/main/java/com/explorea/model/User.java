package com.explorea.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class User {

    @Id
    private Integer id;

    private String googleUserId;

    private String favoriteRoutes;

    private String createdRoutes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public String getFavoriteRoutes() {
        return favoriteRoutes;
    }

    public void setFavoriteRoutes(String favoriteRoutes) {
        this.favoriteRoutes = favoriteRoutes;
    }

    public String getCreatedRoutes() {
        return createdRoutes;
    }

    public void setCreatedRoutes(String createdRoutes) {
        this.createdRoutes = createdRoutes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", googleUserId='" + googleUserId + '\'' +
                ", favoriteRoutes='" + favoriteRoutes + '\'' +
                ", createdRoutes='" + createdRoutes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(googleUserId, user.googleUserId) &&
                Objects.equals(favoriteRoutes, user.favoriteRoutes) &&
                Objects.equals(createdRoutes, user.createdRoutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, googleUserId, favoriteRoutes, createdRoutes);
    }
}
