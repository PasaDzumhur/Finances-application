package com.example.rma20dzumhurpasa47.budgetStuff;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IGraphPresenter {
    ArrayList<Integer> getMonthExpenses() throws ParseException;
    ArrayList<Integer> getMonthIncome() throws ParseException;
    ArrayList<Double> getMonthTotal();
    ArrayList<Double> getWeeklyIncome();
    ArrayList<Double> getWeeklyExpenses();
    ArrayList<Double> getWeeklyTotal();
    ArrayList<Double> getDailyIncome();
    ArrayList<Double> getDailyExpenses();
    ArrayList<Double> getDailyTotal();
    void setTransactions(ArrayList<Transaction> transactions);

}
