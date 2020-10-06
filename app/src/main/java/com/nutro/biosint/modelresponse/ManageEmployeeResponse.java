package com.nutro.biosint.modelresponse;

public class ManageEmployeeResponse {

    private String name;
    private String empDesi;

    public ManageEmployeeResponse() {
    }

    public ManageEmployeeResponse(String name, String empDesi) {
        this.name = name;
        this.empDesi = empDesi;
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
}
