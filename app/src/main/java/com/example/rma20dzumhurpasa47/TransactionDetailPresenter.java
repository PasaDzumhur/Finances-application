package com.example.rma20dzumhurpasa47;

import android.content.Context;

import java.util.Date;

public class TransactionDetailPresenter implements ITransactionDetailPresenter {

    private Context context;
    private Transaction transaction=null;
    public TransactionDetailPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void create(Date date, double amount, String title, Transaction.Type type, String itemDescription, int transactionInterval, Date endDate) {
        this.transaction= new Transaction(date,amount,title,type,itemDescription,transactionInterval,endDate);
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }
}
