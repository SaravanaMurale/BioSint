package com.nutro.biosint.modelrequest;

public class AddExpenseDTO extends EmployeeBaseDTO {

    private String expensePurpuse;
    private String expenseDate;
    private String expenseDetails;
    private String expenseAmount;


    public AddExpenseDTO(String expensePurpuse, String expenseDate, String expenseDetails, String expenseAmount) {
        this.expensePurpuse = expensePurpuse;
        this.expenseDate = expenseDate;
        this.expenseDetails = expenseDetails;
        this.expenseAmount = expenseAmount;
    }

    public AddExpenseDTO(String managerUserId, String empUserId, String expensePurpuse, String expenseDate, String expenseDetails, String expenseAmount) {
        super(managerUserId, empUserId);
        this.expensePurpuse = expensePurpuse;
        this.expenseDate = expenseDate;
        this.expenseDetails = expenseDetails;
        this.expenseAmount = expenseAmount;
    }

    public String getExpensePurpuse() {
        return expensePurpuse;
    }

    public void setExpensePurpuse(String expensePurpuse) {
        this.expensePurpuse = expensePurpuse;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
