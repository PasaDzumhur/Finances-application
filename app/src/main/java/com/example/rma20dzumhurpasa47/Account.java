package com.example.rma20dzumhurpasa47;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class Account {
    private double budget;
    private double totalLimit;
    private double monthLimit;

    public Account(double budget, double totalLimit, double monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public double getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(double monthLimit) {
        this.monthLimit = monthLimit;
    }

    private static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void workTheTransactions(ArrayList<Transaction> list){
        Date today=new Date(System.currentTimeMillis());
        for(Transaction t : list){
            if(t.getDate().after(today)) continue;

            if(t.getType().equals(Transaction.Type.PURCHASE)){
                budget=budget-t.getAmount();
            }else if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME)){
                budget=budget+t.getAmount();
            }else if(t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT)){
                budget=budget-t.getAmount();
            }else if(t.getType().equals(Transaction.Type.REGULARINCOME)){
                Date pom=today;
                if(t.getEndDate().before(pom)) pom=t.getEndDate();
                int daysDuration = getDifferenceDays(t.getDate(),pom);
                int numberOfCalcs=daysDuration/t.getTransactionInterval();
                budget=budget+numberOfCalcs*t.getAmount();
            }else if(t.getType().equals(Transaction.Type.REGULARPAYMENT)){
                Date pom=today;
                if(t.getEndDate().before(pom)) pom=t.getEndDate();
                int daysDuration = getDifferenceDays(t.getDate(),pom);
                int numberOfCalcs=daysDuration/t.getTransactionInterval();
                budget=budget-numberOfCalcs*t.getAmount();
            }
        }
    }
}
