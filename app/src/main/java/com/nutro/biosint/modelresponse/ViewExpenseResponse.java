package com.nutro.biosint.modelresponse;

import com.google.firebase.firestore.PropertyName;

public class ViewExpenseResponse {

    @PropertyName("expensePurpuse")
    private String purposeDetail;
    @PropertyName("expenseDetails")
    private String packsTaken;
    @PropertyName("expenseDate")
    private String viewExpDate;
    private int viewExpStatus;
    @PropertyName("expenseAmount")
    private String viewExpPrice;

    public ViewExpenseResponse(String purposeDetail, String packsTaken, String viewExpDate, int viewExpStatus, String viewExpPrice) {
        this.purposeDetail = purposeDetail;
        this.packsTaken = packsTaken;
        this.viewExpDate = viewExpDate;
        this.viewExpStatus = viewExpStatus;
        this.viewExpPrice = viewExpPrice;
    }

    @PropertyName("expensePurpuse")
    public String getPurposeDetail() {
        return purposeDetail;
    }

    @PropertyName("expensePurpuse")
    public void setPurposeDetail(String purposeDetail) {
        this.purposeDetail = purposeDetail;
    }

    @PropertyName("expenseDetails")
    public String getPacksTaken() {
        return packsTaken;
    }

    @PropertyName("expenseDetails")
    public void setPacksTaken(String packsTaken) {
        this.packsTaken = packsTaken;
    }

    @PropertyName("expenseDate")
    public String getViewExpDate() {
        return viewExpDate;
    }

    @PropertyName("expenseDate")
    public void setViewExpDate(String viewExpDate) {
        this.viewExpDate = viewExpDate;
    }

    public int getViewExpStatus() {
        return viewExpStatus;
    }

    public void setViewExpStatus(int viewExpStatus) {
        this.viewExpStatus = viewExpStatus;
    }

    @PropertyName("expenseAmount")
    public String getViewExpPrice() {
        return viewExpPrice;
    }

    @PropertyName("expenseAmount")
    public void setViewExpPrice(String viewExpPrice) {
        this.viewExpPrice = viewExpPrice;
    }
}
