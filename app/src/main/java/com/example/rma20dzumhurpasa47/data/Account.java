package com.example.rma20dzumhurpasa47.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.example.rma20dzumhurpasa47.list.MainActivity.calendar;

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

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public boolean testLimit(ArrayList<Transaction> list, double limit){
        Date today=new Date(System.currentTimeMillis());
        double sum=0;

        for(Transaction t : list){
            if(t.getDate().after(today)) continue;

            if(t.getType().equals(Transaction.Type.PURCHASE)){
                sum=sum-t.getAmount();
            }else if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME)){
                sum=sum+t.getAmount();
            }else if(t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT)){
                sum=sum-t.getAmount();
            }else if(t.getType().equals(Transaction.Type.REGULARINCOME)){
                Date pom=today;
                if(t.getEndDate().before(pom)) pom=t.getEndDate();
                int daysDuration = getDifferenceDays(t.getDate(),pom);
                int numberOfCalcs=daysDuration/t.getTransactionInterval();
                sum=sum+numberOfCalcs*t.getAmount();
            }else if(t.getType().equals(Transaction.Type.REGULARPAYMENT)){
                Date pom=today;
                if(t.getEndDate().before(pom)) pom=t.getEndDate();
                int daysDuration = getDifferenceDays(t.getDate(),pom);
                int numberOfCalcs=daysDuration/t.getTransactionInterval();
                sum=sum-numberOfCalcs*t.getAmount();
            }
        }
        if(sum>limit) return true;
        return false;

    }

    public boolean testMonthlyLimit(ArrayList<Transaction> list, int month,double limit){
        ArrayList<Transaction> transfilter=new ArrayList<>();

        for(Transaction t : TransactionModel.trans){

            if(month==calendar.get(Calendar.MONTH)) {
                transfilter.add(t);
            }
        }
        return testLimit(transfilter,limit);

    }


    public double workTheTransactions(ArrayList<Transaction> list){
        Date today=new Date(System.currentTimeMillis());
        double sum=0;
        for(Transaction t : list){
            if(t.getDate().after(today)) continue;

            if(t.getType().equals(Transaction.Type.PURCHASE)){
                sum=sum-t.getAmount();
            }else if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME)){
                sum=sum+t.getAmount();
            }else if(t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT)){
                sum=sum-t.getAmount();
            }else if(t.getType().equals(Transaction.Type.REGULARINCOME)){
                Date pom=today;
                if(t.getEndDate().before(pom)) pom=t.getEndDate();
                int daysDuration = getDifferenceDays(t.getDate(),pom);
                int numberOfCalcs=daysDuration/t.getTransactionInterval();
                sum=sum+numberOfCalcs*t.getAmount();
            }else if(t.getType().equals(Transaction.Type.REGULARPAYMENT)){
                Date pom=today;
                if(t.getEndDate().before(pom)) pom=t.getEndDate();
                int daysDuration = getDifferenceDays(t.getDate(),pom);
                int numberOfCalcs=daysDuration/t.getTransactionInterval();
                sum=sum-numberOfCalcs*t.getAmount();
            }
        }
        return sum;
    }

}
