package com.example.rma20dzumhurpasa47;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ITransactionListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setTransactions(ArrayList<Transaction> trans) {

    }

    @Override
    public void notifyTransactionListDataSetChanged() {

    }
}
