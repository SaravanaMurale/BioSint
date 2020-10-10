package com.nutro.biosint.modelrequest;

public class EmployeeBaseDTO {

    private String managerUserId;
    private String empUserId;

    public EmployeeBaseDTO() {
    }

    public EmployeeBaseDTO(String managerUserId, String empUserId) {
        this.managerUserId = managerUserId;
        this.empUserId = empUserId;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }

    public String getEmpUserId() {
        return empUserId;
    }

    public void setEmpUserId(String empUserId) {
        this.empUserId = empUserId;
    }
}
