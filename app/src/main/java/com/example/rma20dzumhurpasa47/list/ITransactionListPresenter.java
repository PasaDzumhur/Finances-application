package com.example.rma20dzumhurpasa47.list;

import androidx.fragment.app.ListFragment;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface ITransactionListPresenter {
    void refreshTransactions();
    void refreshSortPriceAsc() throws ParseException;
    void refreshSortPriceDesc() throws ParseException;
    void refreshSortTitleAsc();
    void refreshSortTitleDesc();
    void refreshSortDateAsc();
    void refreshSortDateDesc();
    ArrayList<Transaction> getTransactions();
    ArrayList<Transaction> getTypeFilter(Transaction.Type type);
}
