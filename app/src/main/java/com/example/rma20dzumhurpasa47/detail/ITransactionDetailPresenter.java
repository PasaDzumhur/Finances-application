package com.example.rma20dzumhurpasa47.detail;

import android.os.Parcelable;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.util.Date;

public interface ITransactionDetailPresenter {
        void create(Date date, double amount, String title, Transaction.Type type, String itemDescription, int transactionInterval, Date endDate);
        Transaction getTransaction();
        void setTransaction(Parcelable transaction);
}
