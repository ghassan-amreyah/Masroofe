package com.example.masroofe;

import static org.junit.Assert.assertEquals;
import  static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.masroofe.LoginActivity;

import org.junit.Assert;
import org.junit.Test;
import java.util.Date;

public class ExpensesUnitTest {


    @Test
    public void testLogin(){
        String account_name = "Test";
        double total_amount = 1545 ;

        Expenses expenses = new Expenses(account_name, total_amount);

        double amount = 500;
    double expected = 1045;
    double new_total = expenses.inverse_expense(amount);

    assertEquals(expected, new_total, 0.000001);
}

    @Test
    public void testLogin2(){
        String account_name = "Test";
        double total_amount = 400 ;

        Expenses expenses = new Expenses(account_name, total_amount);

        double amount = 500;
        double expected = 0;
        double new_total = expenses.inverse_expense(amount);

        assertEquals(expected, new_total, 0.000001);
    }

    @Test
    public void testLogin3(){
        String account_name = "Test";
        double total_amount = 0 ;

        Expenses expenses = new Expenses(account_name, total_amount);

        double amount = 1500;
        double expected = 0;
        double new_total = expenses.add_expense(amount);

        assertEquals(expected, new_total, 0.00001);
    }

    @Test
    public void testLogin4(){
        Cash cash = new Cash(1000);

        double total_cash = cash.getTotal_amount();
        String account_name = "Test";
        double total_amount = 1000 ;

        Expenses expenses = new Expenses(account_name, total_amount);

        double amount = 500;
        double expected = 1500;
        double new_total = expenses.add_expense(amount);

        assertEquals(expected, new_total, 0.00001);
    }
}
