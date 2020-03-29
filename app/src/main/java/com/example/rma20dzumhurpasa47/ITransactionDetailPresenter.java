package com.example.rma20dzumhurpasa47;

import java.util.Date;

public interface ITransactionDetailPresenter {
        void create(Date date, double amount, String title, Transaction.Type type, String itemDescription, int transactionInterval, Date endDate);
        Transaction getTransaction();
}
