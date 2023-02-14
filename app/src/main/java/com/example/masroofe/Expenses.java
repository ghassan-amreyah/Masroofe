package com.example.masroofe;

public class Expenses {
    private String account_name;
    private double total_amount;

    public Expenses(String account_name, double total_amount) {
        this.account_name = account_name;
        this.total_amount = total_amount;
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

    // Debit Expenses
    public double add_expense (double amount) {
        double available = 1000;
        if(amount <= available) {
            total_amount += amount;

            Cash cash = new Cash();

            double new_monthly_cash = cash.getMonthly_amount() - amount;
            cash.setMonthly_amount(new_monthly_cash);

            double new_cash = cash.getTotal_amount() - amount;
            cash.setTotal_amount(new_cash);
            return total_amount;
        }
        else{
            return 0;
        }
    }

    // Credit Expenses
    public double inverse_expense (double amount) {

        if(amount <= total_amount) {
            total_amount -= amount;

            Cash cash = new Cash();
            double new_monthly_cash = cash.getMonthly_amount() - amount;
            cash.setMonthly_amount(new_monthly_cash);

            double new_cash = cash.getTotal_amount() - amount;
            cash.setTotal_amount(new_cash);

            return total_amount;
        }else{
            return 0;
        }
    }
}
