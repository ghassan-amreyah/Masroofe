package com.example.masroofe;

import java.util.Date;

public class Record {
    private String account_name;
    private String amount;
    private String date;

    public Record(String account_name, String amount, String date) {
        this.account_name = account_name;
        this.amount = amount;
        this.date = date;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
