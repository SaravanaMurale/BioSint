package com.nutro.biosint.modelrequest;

public class UserDTO {

    private String userId;
    private String mobileNumber;
    private String deviceId;
    private boolean userActiveStatus;
    private int userRole;
    private String email;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String userId, String mobileNumber, String deviceId, boolean userActiveStatus, int userRole, String email, String password) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
        this.userActiveStatus = userActiveStatus;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isUserActiveStatus() {
        return userActiveStatus;
    }

    public void setUserActiveStatus(boolean userActiveStatus) {
        this.userActiveStatus = userActiveStatus;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
