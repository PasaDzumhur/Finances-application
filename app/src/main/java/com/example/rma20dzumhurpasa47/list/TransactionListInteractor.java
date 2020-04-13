package com.example.rma20dzumhurpasa47.list;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;
import com.example.rma20dzumhurpasa47.list.ITransactionListInteractor;
import com.example.rma20dzumhurpasa47.list.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.rma20dzumhurpasa47.list.MainActivity.calendar;

public class TransactionListInteractor implements ITransactionListInteractor {
    private Calendar pom=Calendar.getInstance();
    @Override
    public ArrayList<Transaction> get() {

        String string= MainActivity.getFilter();
        if(string.equalsIgnoreCase("PURCHASE")) {
            return getPurchase();
        }
        else if(string.equals("INDIVIDUALPAYMENT")) {
            return getIndividualPayment();
        }
        else if(string.equalsIgnoreCase("REGULARPAYMENT")) {
            return getRegularPayment();
        }
        else if(string.equalsIgnoreCase("INDIVIDUALINCOME")) {
            return getIndividualIncome();
        }
        else if(string.equalsIgnoreCase("REGULARINCOME")) {
            return getRegularIncome();
        }
        else {
            return getClassic();
        }
    }

    public ArrayList<Transaction> getClassic() {


        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME) || t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT) || t.getType().equals(Transaction.Type.PURCHASE)){
                pom.setTime(t.getDate());
                if(pom.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && pom.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    transfilter.add(t);
                }
            }else{
                if(pom.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && pom.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) transfilter.add(t);
                else{
                    Calendar pom2=Calendar.getInstance();
                    pom2.setTime(t.getEndDate());
                    if(pom.get(Calendar.MONTH)!=pom2.get(Calendar.MONTH) || pom.get(Calendar.YEAR)!=pom2.get(Calendar.YEAR)){
                        while (true){
                            pom.add(Calendar.MONTH,1);
                            if(pom.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && pom.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)){
                                transfilter.add(t);
                                break;
                            }
                            if(pom.get(Calendar.MONTH)!=pom2.get(Calendar.MONTH) || pom.get(Calendar.YEAR)!=pom2.get(Calendar.YEAR)) break;
                        }
                    }
                }
            }
        }
        return transfilter;

    }

    @Override
    public ArrayList<Transaction> getPurchase() {

        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter){
            if (t.getType().equals(Transaction.Type.PURCHASE)) transfilter.add(t);
        }
        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getIndividualPayment() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT)) transfilter.add(t);
        }

        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getRegularPayment() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.REGULARPAYMENT)) transfilter.add(t);
        }

        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getIndividualIncome() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.INDIVIDUALINCOME)) transfilter.add(t);
        }

        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getRegularIncome() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.REGULARINCOME)) transfilter.add(t);
        }

        return transfilter;
    }


}
