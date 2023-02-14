package com.example.masroofe;

public class Cash {
    private double total_amount = 0;
    private double monthly_amount = 0;

    public Cash(double total_amount) {
        this.total_amount = total_amount;
    }

    public Cash() {
        this.total_amount = total_amount;
        this.monthly_amount = monthly_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getMonthly_amount() {
        return monthly_amount;
    }

    public void setMonthly_amount(double monthly_amount) {
        this.monthly_amount = monthly_amount;
    }
}
