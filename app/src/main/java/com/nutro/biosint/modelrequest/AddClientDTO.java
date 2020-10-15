package com.nutro.biosint.modelrequest;

public class AddClientDTO {

    private String clientName;
    private String clientDesig;
    private String clientOrg;
    private String clientMobileNumber;
    private String clientEmail;
    private String clientAddr;
    private String clientDetails;
    private String clientRadioBtn;
    private String managerUserId;
    private String empId;
    private String dateAndTime;
    private boolean isClientAssigned;
    private int workProgress;
    private String clientDocId;

    public AddClientDTO() {
    }

    public AddClientDTO(String clientName, String clientDesig, String clientOrg, String clientMobileNumber, String clientEmail, String clientAddr, String clientDetails, String clientRadioBtn, String managerUserId, String empId, String dateAndTime, boolean isClientAssigned, int workProgress, String clientDocId) {
        this.clientName = clientName;
        this.clientDesig = clientDesig;
        this.clientOrg = clientOrg;
        this.clientMobileNumber = clientMobileNumber;
        this.clientEmail = clientEmail;
        this.clientAddr = clientAddr;
        this.clientDetails = clientDetails;
        this.clientRadioBtn = clientRadioBtn;
        this.managerUserId = managerUserId;
        this.empId = empId;
        this.dateAndTime = dateAndTime;
        this.isClientAssigned = isClientAssigned;
        this.workProgress = workProgress;
        this.clientDocId = clientDocId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientDesig() {
        return clientDesig;
    }

    public void setClientDesig(String clientDesig) {
        this.clientDesig = clientDesig;
    }

    public String getClientOrg() {
        return clientOrg;
    }

    public void setClientOrg(String clientOrg) {
        this.clientOrg = clientOrg;
    }

    public String getClientMobileNumber() {
        return clientMobileNumber;
    }

    public void setClientMobileNumber(String clientMobileNumber) {
        this.clientMobileNumber = clientMobileNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientAddr() {
        return clientAddr;
    }

    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
    }

    public String getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(String clientDetails) {
        this.clientDetails = clientDetails;
    }

    public String getClientRadioBtn() {
        return clientRadioBtn;
    }

    public void setClientRadioBtn(String clientRadioBtn) {
        this.clientRadioBtn = clientRadioBtn;
    }

    public String getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(String managerUserId) {
        this.managerUserId = managerUserId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public boolean isClientAssigned() {
        return isClientAssigned;
    }

    public void setClientAssigned(boolean clientAssigned) {
        isClientAssigned = clientAssigned;
    }

    public int getWorkProgress() {
        return workProgress;
    }

    public void setWorkProgress(int workProgress) {
        this.workProgress = workProgress;
    }

    public String getClientDocId() {
        return clientDocId;
    }

    public void setClientDocId(String clientDocId) {
        this.clientDocId = clientDocId;
    }
}
