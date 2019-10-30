package com.explorea.model;

import org.springframework.data.annotation.Id;

public class Rating {

    @Id
    private Integer id;

    private Integer userId;

    private Integer routeId;

    private Integer val;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", userId=" + userId +
                ", routeId=" + routeId +
                ", val=" + val +
                '}';
    }
}
