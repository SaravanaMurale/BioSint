package com.nutro.biosint.modelresponse;

public class ViewCheckInResponse {

    private String viewCheckInName;
    private String viewCheckInDetails;
    private String viewCheckInAddress;
    private String viewCheckInDate;

    public ViewCheckInResponse() {
    }

    public ViewCheckInResponse(String viewCheckInName, String viewCheckInDetails, String viewCheckInAddress, String viewCheckInDate) {
        this.viewCheckInName = viewCheckInName;
        this.viewCheckInDetails = viewCheckInDetails;
        this.viewCheckInAddress = viewCheckInAddress;
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

    public String getViewCheckInAddress() {
        return viewCheckInAddress;
    }

    public void setViewCheckInAddress(String viewCheckInAddress) {
        this.viewCheckInAddress = viewCheckInAddress;
    }

    public String getViewCheckInDate() {
        return viewCheckInDate;
    }

    public void setViewCheckInDate(String viewCheckInDate) {
        this.viewCheckInDate = viewCheckInDate;
    }
}
