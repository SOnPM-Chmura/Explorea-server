package com.explorea;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class VerifiedGoogleUserId {

    private String googleUserId;
    private HttpStatus httpStatus;

    public VerifiedGoogleUserId(String googleUserId, HttpStatus httpStatus) {
        this.googleUserId = googleUserId;
        this.httpStatus = httpStatus;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "VerifiedGoogleUserId{" +
                "googleUserId='" + googleUserId + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerifiedGoogleUserId that = (VerifiedGoogleUserId) o;
        return Objects.equals(googleUserId, that.googleUserId) &&
                httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(googleUserId, httpStatus);
    }
}
