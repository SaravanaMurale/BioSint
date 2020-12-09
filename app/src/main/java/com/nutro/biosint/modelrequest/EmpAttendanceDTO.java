package com.nutro.biosint.modelrequest;

import com.google.firebase.firestore.GeoPoint;

public class EmpAttendanceDTO extends EmployeeBaseDTO {

    private String empLoginDate;
    private String empAttendanceTime;


    public EmpAttendanceDTO(String managerUserId, String empUserId, String empLoginDate, String empAttendanceTime) {
        super(managerUserId, empUserId);
        this.empLoginDate = empLoginDate;
        this.empAttendanceTime = empAttendanceTime;
    }

    public String getEmpLoginDate() {
        return empLoginDate;
    }

    public void setEmpLoginDate(String empLoginDate) {
        this.empLoginDate = empLoginDate;
    }

    public String getEmpAttendanceTime() {
        return empAttendanceTime;
    }

    public void setEmpAttendanceTime(String empAttendanceTime) {
        this.empAttendanceTime = empAttendanceTime;
    }
}
