package com.example.rma20dzumhurpasa47.budgetStuff;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IGraphPresenter {
    ArrayList<Integer> getMonthExpenses() throws ParseException;
    ArrayList<Integer> getMonthIncome() throws ParseException;
    //ArrayList<Integer> getMonthTotal();
    ArrayList<Integer> getWeeklyIncome();
    ArrayList<Integer> getWeeklyExpenses();
    //ArrayList<Integer> getWeeklyTotal();
    ArrayList<Integer> getDailyIncome();
    ArrayList<Integer> getDailyExpenses();
    //ArrayList<Integer> getDailyTotal();
    void setTransactions(ArrayList<Transaction> transactions);

}
