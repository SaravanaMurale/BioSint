package com.nutro.biosint.modelrequest;

public class EmployeeBaseDTO {

    private String managerUserId;

    public EmployeeBaseDTO() {
    }

    public EmployeeBaseDTO(String managerUserId) {
        this.managerUserId = managerUserId;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }
}
