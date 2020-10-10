package com.nutro.biosint.modelresponse;

public class UserResponse {

    private String deviceId;
    private String mobileNumber;
    private boolean userActiveStatus;
    private String userId;
    private String email;
    private int userRole;
    private String managerUserId;

    public UserResponse() {
    }

    public UserResponse(String deviceId, String mobileNumber, boolean userActiveStatus, String userId, String email, int userRole, String managerUserId) {
        this.deviceId = deviceId;
        this.mobileNumber = mobileNumber;
        this.userActiveStatus = userActiveStatus;
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
        this.managerUserId = managerUserId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isUserActiveStatus() {
        return userActiveStatus;
    }

    public void setUserActiveStatus(boolean userActiveStatus) {
        this.userActiveStatus = userActiveStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }
}
