package com.example.rma20dzumhurpasa47.budgetStuff;

import android.content.Context;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.list.ITransactionListInteractor;
import com.example.rma20dzumhurpasa47.list.MainActivity;
import com.example.rma20dzumhurpasa47.list.TransactionListInteractor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class GraphPresenter implements IGraphPresenter,TransactionListInteractor.TransactionSearchDone {

    private Context context;
    private ArrayList<Transaction> transactions=new ArrayList<>();
    private  ITransactionListInteractor interactor;
    private Calendar pom=Calendar.getInstance();
    private TransactionListInteractor.TransactionSearchDone caller;

    public GraphPresenter(Context context, TransactionListInteractor.TransactionSearchDone caller) {
        this.interactor=new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"date.asc");
        this.context = context;
        this.caller = caller;
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
                    int vrijednost= (int) t.monthlyAmount(i);
                    //pom.set(Calendar.MONTH,i);
                    ret.set(i,ret.get(i)+vrijednost);
                    //System.out.print(vrijednost+" ");
                }
                //System.out.print("");
            }
        }
        //for(Integer pom : ret ) System.out.println(pom);
        return ret;
        //ArrayList<Integer> pom=new ArrayList<>();
        //for(int i=1; i<=12; i++) pom.add(i);
        //return pom;
    }

    @Override
    public ArrayList<Integer> getMonthIncome() throws ParseException {

        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<12; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME) || t.getType().equals(Transaction.Type.REGULARINCOME)){

                for(int i=0; i<12; i++){
                    int vrijednost= (int) t.monthlyAmount(i);

                    ret.set(i,ret.get(i)+vrijednost);

                }

            }
        }
        //for(Integer pom : ret ) System.out.println(pom);
        return ret;

    }


    @Override
    public ArrayList<Integer> getWeeklyIncome() {
        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<6; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME) || t.getType().equals(Transaction.Type.REGULARINCOME)){
                pom.setTime(t.getDate());
                int week=pom.get(Calendar.WEEK_OF_MONTH);
                ret.set(week-1, (int) (ret.get(week-1)+t.getAmount()));
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Integer> getWeeklyExpenses() {
        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<6; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.PURCHASE) || t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT) || t.getType().equals(Transaction.Type.REGULARPAYMENT)){
                pom.setTime(t.getDate());
                int week=pom.get(Calendar.WEEK_OF_MONTH);
                ret.set(week-1, (int) (ret.get(week-1)+t.getAmount()));
            }
        }
        return ret;
    }


    @Override
    public ArrayList<Integer> getDailyIncome() {
        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<31; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME) || t.getType().equals(Transaction.Type.REGULARINCOME)){
                pom.setTime(t.getDate());
                int week=pom.get(Calendar.DAY_OF_MONTH);
                ret.set(week, (int) (ret.get(week)+t.getAmount()));
            }
        }
        return ret;
    }

    @Override
    public ArrayList<Integer> getDailyExpenses() {
        ArrayList<Integer> ret=new ArrayList<>();
        for(int i=0; i<31; i++) ret.add(0);
        pom.setTime(MainActivity.calendar.getTime());
        for(Transaction t : transactions){
            if(t.getType().equals(Transaction.Type.PURCHASE) || t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT) || t.getType().equals(Transaction.Type.REGULARPAYMENT)){
                pom.setTime(t.getDate());
                int week=pom.get(Calendar.DAY_OF_MONTH);
                ret.set(week, (int) (ret.get(week)+t.getAmount()));
            }
        }
        return ret;
    }




    @Override
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions=transactions;
    }

    @Override
    public void onDone(ArrayList<Transaction> results) {
        this.transactions=results;
        caller.onDone(results);

    }
}
