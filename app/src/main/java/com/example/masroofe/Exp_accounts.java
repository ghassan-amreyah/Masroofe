package com.example.masroofe;

import java.util.ArrayList;
import java.util.List;

public class Exp_accounts {

    private ArrayList<Object> expenses = new ArrayList<>();

    public Exp_accounts(){

    }

    public List<Object> getExpenseAccounts(){
        ArrayList<Object> data = new ArrayList<>();
        for(Object x : expenses){
            data.add(x);
        }
        return data;
    }

    private void Add_ExpenseAccounts(Object exp){
        expenses.add(exp);
    }
}
