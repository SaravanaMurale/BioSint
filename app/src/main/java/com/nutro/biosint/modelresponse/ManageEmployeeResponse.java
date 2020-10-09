package com.nutro.biosint.modelresponse;

public class ManageEmployeeResponse {


    private String name;
    private String empDesi;

    private String deviceId;
    private String email;
    private String empStartWorkTime;
    private String empEndWorkTime;
    private String managerUserId;
    private String mobileNumber;
    private String userId;
    private int userRole;

    public ManageEmployeeResponse() {
    }

    public ManageEmployeeResponse(String name, String empDesi) {
        this.name = name;
        this.empDesi = empDesi;
    }

    public ManageEmployeeResponse(String deviceId, String email, String empStartWorkTime, String empEndWorkTime, String managerUserId, String mobileNumber, String userId, int userRole) {
        this.deviceId = deviceId;
        this.email = email;
        this.empStartWorkTime = empStartWorkTime;
        this.empEndWorkTime = empEndWorkTime;
        this.managerUserId = managerUserId;
        this.mobileNumber = mobileNumber;
        this.userId = userId;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpDesi() {
        return empDesi;
    }

    public void setEmpDesi(String empDesi) {
        this.empDesi = empDesi;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpStartWorkTime() {
        return empStartWorkTime;
    }

    public void setEmpStartWorkTime(String empStartWorkTime) {
        this.empStartWorkTime = empStartWorkTime;
    }

    public String getEmpEndWorkTime() {
        return empEndWorkTime;
    }

    public void setEmpEndWorkTime(String empEndWorkTime) {
        this.empEndWorkTime = empEndWorkTime;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
