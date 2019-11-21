package com.explorea.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class User {

    @Id
    private Integer id;

    private String googleUserId;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", googleUserId='" + googleUserId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(googleUserId, user.googleUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, googleUserId);
    }
}
