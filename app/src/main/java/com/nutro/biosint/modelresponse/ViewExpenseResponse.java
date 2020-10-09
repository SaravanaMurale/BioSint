package com.nutro.biosint.modelresponse;

public class ViewExpenseResponse {

    private String purposeDetail;
    private String packsTaken;
    private String viewExpDate;
    private int viewExpStatus;
    private String viewExpPrice;

    public ViewExpenseResponse(String purposeDetail, String packsTaken, String viewExpDate, int viewExpStatus, String viewExpPrice) {
        this.purposeDetail = purposeDetail;
        this.packsTaken = packsTaken;
        this.viewExpDate = viewExpDate;
        this.viewExpStatus = viewExpStatus;
        this.viewExpPrice = viewExpPrice;
    }

    public String getPurposeDetail() {
        return purposeDetail;
    }

    public void setPurposeDetail(String purposeDetail) {
        this.purposeDetail = purposeDetail;
    }

    public String getPacksTaken() {
        return packsTaken;
    }

    public void setPacksTaken(String packsTaken) {
        this.packsTaken = packsTaken;
    }

    public String getViewExpDate() {
        return viewExpDate;
    }

    public void setViewExpDate(String viewExpDate) {
        this.viewExpDate = viewExpDate;
    }

    public int getViewExpStatus() {
        return viewExpStatus;
    }

    public void setViewExpStatus(int viewExpStatus) {
        this.viewExpStatus = viewExpStatus;
    }

    public String getViewExpPrice() {
        return viewExpPrice;
    }

    public void setViewExpPrice(String viewExpPrice) {
        this.viewExpPrice = viewExpPrice;
    }
}
