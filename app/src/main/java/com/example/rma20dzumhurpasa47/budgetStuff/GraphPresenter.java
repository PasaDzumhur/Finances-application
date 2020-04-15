package com.example.rma20dzumhurpasa47.budgetStuff;

import android.content.Context;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.list.MainActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class GraphPresenter implements IGraphPresenter {

    private Context context;
    private ArrayList<Transaction> transactions=new ArrayList<>();
    private Calendar pom=Calendar.getInstance();

    public GraphPresenter(Context context) {
        this.context = context;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }



    @Override
    public ArrayList<Integer> getMonthExpenses() throws ParseException {
        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<12; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.PURCHASE) || t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT) || t.getType().equals(Transaction.Type.REGULARPAYMENT)){
                System.out.print(t.getTitle()+" : ");
                for(int i=0; i<12; i++){
                    int vrijednost= (int) t.monthlyAmount(i+1);
                    //pom.set(Calendar.MONTH,i);
                    ret.set(i,ret.get(i)+vrijednost);
                    //System.out.print(vrijednost+" ");
                }
                //System.out.print("");
            }
        }
        //for(Integer pom : ret ) System.out.println(pom);
        return ret;
    }

    @Override
    public ArrayList<Integer> getMonthIncome() throws ParseException {

        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<12; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME) || t.getType().equals(Transaction.Type.REGULARINCOME)){
                System.out.print(t.getTitle()+" : ");
                for(int i=0; i<12; i++){
                    int vrijednost= (int) t.monthlyAmount(i+1);
                    //pom.set(Calendar.MONTH,i);
                    ret.set(i,ret.get(i)+vrijednost);
                    //System.out.print(vrijednost+" ");
                }
                //System.out.print("");
            }
        }
        //for(Integer pom : ret ) System.out.println(pom);
        return ret;
    }

    @Override
    public ArrayList<Double> getMonthTotal() {
        return null;
    }

    @Override
    public ArrayList<Double> getWeeklyIncome() {
        return null;
    }

    @Override
    public ArrayList<Double> getWeeklyExpenses() {
        return null;
    }

    @Override
    public ArrayList<Double> getWeeklyTotal() {
        return null;
    }

    @Override
    public ArrayList<Double> getDailyIncome() {
        return null;
    }

    @Override
    public ArrayList<Double> getDailyExpenses() {
        return null;
    }

    @Override
    public ArrayList<Double> getDailyTotal() {
        return null;
    }

    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions=transactions;
    }
}
