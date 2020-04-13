package com.example.rma20dzumhurpasa47.list;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.util.ArrayList;

public interface ITransactionListView {
    void setTransactions(ArrayList<Transaction> trans);
    void notifyTransactionListDataSetChanged();
}
