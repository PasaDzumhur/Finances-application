package com.example.rma20dzumhurpasa47;

import java.text.ParseException;

public interface ITransactionListPresenter {
    void refreshTransactions();
    void refreshSortPriceAsc() throws ParseException;
    void refreshSortPriceDesc() throws ParseException;
    void refreshSortTitleAsc();
    void refreshSortTitleDesc();
    void refreshSortDateAsc();
    void refreshSortDateDesc();
}
