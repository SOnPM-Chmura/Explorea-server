package com.explorea.model;

import org.springframework.data.annotation.Id;

public class User {

    //@Id
    private Integer id;

    private String email;

    private String favoriteRoutes;

    private String createdRoutes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                ", email='" + email + '\'' +
                ", favoriteRoutes='" + favoriteRoutes + '\'' +
                ", createdRoutes='" + createdRoutes + '\'' +
                '}';
    }
}
