package com.example.rma20dzumhurpasa47.detail;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.detail.ITransactionDetailPresenter;

import java.util.Date;

public class TransactionDetailPresenter implements ITransactionDetailPresenter,TransactionDetailInteractor.TransactionUpdateDone {

    private Context context;
    private Transaction transaction=null;
    public TransactionDetailPresenter(Context context) {
        this.context = context;
    }
    private ITransactionDetailInteractor interactor;

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

    public void execute(boolean delete, boolean add,boolean save){
        interactor = new TransactionDetailInteractor(context,transaction,(TransactionDetailInteractor.TransactionUpdateDone)this,delete,add,save);
    }

    @Override
    public void onDone() {
        Log.e("OnDone","OnDone");
    }
}
