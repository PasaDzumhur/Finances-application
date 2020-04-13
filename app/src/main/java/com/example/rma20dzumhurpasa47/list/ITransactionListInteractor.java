package com.example.rma20dzumhurpasa47.list;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.util.ArrayList;

public interface ITransactionListInteractor {
    ArrayList<Transaction> get();
    ArrayList<Transaction> getPurchase();
    ArrayList<Transaction> getIndividualPayment();
    ArrayList<Transaction> getRegularPayment();
    ArrayList<Transaction> getIndividualIncome();
    ArrayList<Transaction> getRegularIncome();
    ArrayList<Transaction> getClassic();
}
