package com.example.rma20dzumhurpasa47;

import java.util.ArrayList;

public class TransactionListInteractor implements ITransactionListInteractor {
    @Override
    public ArrayList<Transaction> get() {
        System.out.println(MainActivity.getFilter());
        String string=MainActivity.getFilter();
        if(string.equals("PURCHASE")) {
            return getPurchase();
        }
        else if(string.equals("INDIVIDUALPAYMENT")) {
            return getIndividualPayment();
        }
        else if(string.equals("REGULARPAYMENT")) {
            return getRegularPayment();
        }
        else if(string.equals("INDIVIDUALINCOME")) {
            return getIndividualIncome();
        }
        else if(string.equals("REGULARINCOME")) {
            return getRegularIncome();
        }
        else {
            return getClassic();
        }
    }

    public ArrayList<Transaction> getClassic() {
        return TransactionModel.getTrans();
    }

    @Override
    public ArrayList<Transaction> getPurchase() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.PURCHASE)) transfilter.add(t);
        }
        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getIndividualPayment() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT)) transfilter.add(t);
        }
        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getRegularPayment() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.REGULARPAYMENT)) transfilter.add(t);
        }
        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getIndividualIncome() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME)) transfilter.add(t);
        }
        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getRegularIncome() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.REGULARINCOME)) transfilter.add(t);
        }
        return transfilter;
    }
    /*
    @Override
    public ArrayList<Transaction> get(String string) {
        if(string.equals("PURCHASE")) return getPurchase();
        else if(string.equals("INDIVIDUALPAYMENT")) return getIndividualPayment();
        else if(string.equals("REGULARPAYMENT")) return getRegularPayment();
        else if(string.equals("INDIVIDUALINCOME")) return getIndividualIncome();
        else if(string.equals("REGULARINCOME")) return getRegularIncome();
        else return get();
    }*/

}
