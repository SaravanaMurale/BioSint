package com.nutro.biosint.modelrequest;

public class AddEmployeeDTO {

    String userId;
    String managerUserId;
    String name;
    String email;
    String password;
    String mobileNumber;
    String deviceId;
    String empDesi;
    String empStartWorkTime;
    String empEndWorkTime;
    int userRole;
    private boolean userActiveStatus;

    /*public AddEmployeeDTO() {
    }*/

    public AddEmployeeDTO(String userId, String managerUserId, String name, String email, String password, String mobileNumber, String deviceId, String empDesi, String empStartWorkTime, String empEndWorkTime, int userRole, boolean userActiveStatus) {
        this.userId = userId;
        this.managerUserId = managerUserId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
        this.empDesi = empDesi;
        this.empStartWorkTime = empStartWorkTime;
        this.empEndWorkTime = empEndWorkTime;
        this.userRole = userRole;
        this.userActiveStatus = userActiveStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmpDesi() {
        return empDesi;
    }

    public void setEmpDesi(String empDesi) {
        this.empDesi = empDesi;
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

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public boolean isUserActiveStatus() {
        return userActiveStatus;
    }

    public void setUserActiveStatus(boolean userActiveStatus) {
        this.userActiveStatus = userActiveStatus;
    }
}
