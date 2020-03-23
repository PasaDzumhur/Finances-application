package com.example.rma20dzumhurpasa47;

import java.util.ArrayList;

public class TransactionListInteractor implements ITransactionListInteractor {
    @Override
    public ArrayList<Transaction> get() {
        return TransactionModel.getTrans();
    }
}
