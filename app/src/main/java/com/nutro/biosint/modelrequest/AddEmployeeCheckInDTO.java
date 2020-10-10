package com.nutro.biosint.modelrequest;

import com.google.firebase.firestore.GeoPoint;

public class AddEmployeeCheckInDTO extends EmployeeBaseDTO {

    private String checkInName;
    private String checkInDetails;
    private GeoPoint geoPoint;
    private String date;


    public AddEmployeeCheckInDTO() {
    }

    public AddEmployeeCheckInDTO(String checkInName, String checkInDetails, GeoPoint geoPoint, String date) {
        this.checkInName = checkInName;
        this.checkInDetails = checkInDetails;
        this.geoPoint = geoPoint;
        this.date = date;
    }

    public AddEmployeeCheckInDTO(String managerUserId, String empUserId, String checkInName, String checkInDetails, GeoPoint geoPoint, String date) {
        super(managerUserId, empUserId);
        this.checkInName = checkInName;
        this.checkInDetails = checkInDetails;
        this.geoPoint = geoPoint;
        this.date = date;
    }

    public String getCheckInName() {
        return checkInName;
    }

    public void setCheckInName(String checkInName) {
        this.checkInName = checkInName;
    }

    public String getCheckInDetails() {
        return checkInDetails;
    }

    public void setCheckInDetails(String checkInDetails) {
        this.checkInDetails = checkInDetails;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
