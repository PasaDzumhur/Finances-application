package com.example.rma20dzumhurpasa47;

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
