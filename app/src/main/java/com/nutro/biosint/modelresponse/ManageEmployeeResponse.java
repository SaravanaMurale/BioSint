package com.nutro.biosint.modelresponse;

public class ManageEmployeeResponse {

    private String empName;
    private String empDesignation;

    public ManageEmployeeResponse() {
    }

    public ManageEmployeeResponse(String empName, String empDesignation) {
        this.empName = empName;
        this.empDesignation = empDesignation;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }
}
