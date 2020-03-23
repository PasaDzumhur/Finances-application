package com.example.rma20dzumhurpasa47;

import java.util.ArrayList;

public interface ITransactionListView {
    void setTransactions(ArrayList<Transaction> trans);
    void notifyTransactionListDataSetChanged();
}
