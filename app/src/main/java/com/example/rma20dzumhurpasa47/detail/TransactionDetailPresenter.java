package com.example.rma20dzumhurpasa47.detail;

import android.content.Context;
import android.os.Parcelable;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.detail.ITransactionDetailPresenter;

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

    @Override
    public void setTransaction(Parcelable transaction) {
        this.transaction=(Transaction) transaction;
    }
}
