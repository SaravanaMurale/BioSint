package com.nutro.biosint.modelresponse;

import com.google.firebase.firestore.GeoPoint;

public class ViewCheckInResponse {

    private String viewCheckInName;
    private String viewCheckInDetails;
    private GeoPoint geoPoint;
    private String viewCheckInDate;

    public ViewCheckInResponse() {
    }

    public ViewCheckInResponse(String viewCheckInName, String viewCheckInDetails, GeoPoint geoPoint, String viewCheckInDate) {
        this.viewCheckInName = viewCheckInName;
        this.viewCheckInDetails = viewCheckInDetails;
        this.geoPoint = geoPoint;
        this.viewCheckInDate = viewCheckInDate;
    }

    public String getViewCheckInName() {
        return viewCheckInName;
    }

    public void setViewCheckInName(String viewCheckInName) {
        this.viewCheckInName = viewCheckInName;
    }

    public String getViewCheckInDetails() {
        return viewCheckInDetails;
    }

    public void setViewCheckInDetails(String viewCheckInDetails) {
        this.viewCheckInDetails = viewCheckInDetails;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getViewCheckInDate() {
        return viewCheckInDate;
    }

    public void setViewCheckInDate(String viewCheckInDate) {
        this.viewCheckInDate = viewCheckInDate;
    }
}
