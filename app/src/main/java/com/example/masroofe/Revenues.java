package com.example.masroofe;

public class Revenues {
    private String account_name;
    private double total_amount;
    private double monthly_amount;

    public Revenues(String account_name, double total_amount, double monthly_amount) {
        this.account_name = account_name;
        this.total_amount = total_amount;
        this.monthly_amount = monthly_amount;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
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

    // Debit revenues
    public void inverse_rev (double amount) {
        Cash cash = new Cash();
        double available = cash.getMonthly_amount();
        if(amount <= available) {
            total_amount -= amount;
            monthly_amount -= amount;

            double new_monthly_cash = cash.getMonthly_amount() - amount;
            cash.setMonthly_amount(new_monthly_cash);

            double new_cash = cash.getTotal_amount() - amount;
            cash.setTotal_amount(new_cash);
        }
    }

    // Credit revenues
    public void add_rev (double amount) {
        total_amount += amount;
        monthly_amount += amount;

        Cash cash = new Cash();
        double new_monthly_cash = cash.getMonthly_amount()+amount;
        cash.setMonthly_amount(new_monthly_cash);

        double new_cash = cash.getTotal_amount()+amount;
        cash.setTotal_amount(new_cash);
    }
}
