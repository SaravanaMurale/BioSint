package com.nutro.biosint.modelresponse;

public class UserResponse {

    private String deviceId;
    private String mobileNumber;
    private boolean userActiveStatus;
    private String userId;
    private String email;

    public UserResponse() {
    }

    public UserResponse(String userId, String mobileNumber, String deviceId, boolean userActiveStatus, String email) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
        this.userActiveStatus = userActiveStatus;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isUserActiveStatus() {
        return userActiveStatus;
    }

    public String getEmail() {
        return email;
    }
}
