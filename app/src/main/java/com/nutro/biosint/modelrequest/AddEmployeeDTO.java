package com.nutro.biosint.modelrequest;

public class AddEmployeeDTO {

    String managerUserId;
    String employeeUserId;
    String empName;
    String empEmailId;
    String empPassword;
    String empMobileNum;
    String empDesi;
    String empStartWorkTime;
    String empEndWorkTime;
    int role;

    public AddEmployeeDTO() {
    }

    public AddEmployeeDTO(String managerUserId, String employeeUserId, String empName, String empEmailId, String empPassword, String empMobileNum, String empDesi, String empStartWorkTime, String empEndWorkTime,int role) {
        this.managerUserId = managerUserId;
        this.employeeUserId = employeeUserId;
        this.empName = empName;
        this.empEmailId = empEmailId;
        this.empPassword = empPassword;
        this.empMobileNum = empMobileNum;
        this.empDesi = empDesi;
        this.empStartWorkTime = empStartWorkTime;
        this.empEndWorkTime = empEndWorkTime;
        this.role=role;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }

    public String getEmployeeUserId() {
        return employeeUserId;
    }

    public void setEmployeeUserId(String employeeUserId) {
        this.employeeUserId = employeeUserId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpMobileNum() {
        return empMobileNum;
    }

    public void setEmpMobileNum(String empMobileNum) {
        this.empMobileNum = empMobileNum;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
