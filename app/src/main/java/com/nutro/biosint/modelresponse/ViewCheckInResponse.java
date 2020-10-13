package com.nutro.biosint.modelresponse;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.PropertyName;

public class ViewCheckInResponse {

    @PropertyName("checkInName")
    private String viewCheckInName;
    @PropertyName("checkInDetails")
    private String viewCheckInDetails;
    @PropertyName("geoPoint")
    private GeoPoint geoPoint;
    @PropertyName("date")
    private String viewCheckInDate;

    public ViewCheckInResponse() {
    }

    public ViewCheckInResponse(String viewCheckInName, String viewCheckInDetails, GeoPoint geoPoint, String viewCheckInDate) {
        this.viewCheckInName = viewCheckInName;
        this.viewCheckInDetails = viewCheckInDetails;
        this.geoPoint = geoPoint;
        this.viewCheckInDate = viewCheckInDate;
    }

    @PropertyName("checkInName")
    public String getViewCheckInName() {
        return viewCheckInName;
    }
    @PropertyName("checkInName")
    public void setViewCheckInName(String viewCheckInName) {
        this.viewCheckInName = viewCheckInName;
    }

    @PropertyName("checkInDetails")
    public String getViewCheckInDetails() {
        return viewCheckInDetails;
    }
    @PropertyName("checkInDetails")
    public void setViewCheckInDetails(String viewCheckInDetails) {
        this.viewCheckInDetails = viewCheckInDetails;
    }

    @PropertyName("geoPoint")
    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
    @PropertyName("geoPoint")
    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    @PropertyName("date")
    public String getViewCheckInDate() {
        return viewCheckInDate;
    }
    @PropertyName("date")
    public void setViewCheckInDate(String viewCheckInDate) {
        this.viewCheckInDate = viewCheckInDate;
    }
}
