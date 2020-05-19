package com.example.rma20dzumhurpasa47.budgetStuff;

import android.os.AsyncTask;

import com.example.rma20dzumhurpasa47.data.Transaction;

import java.util.ArrayList;

public class GraphInteractor extends AsyncTask<String,Integer,Void> implements IGraphInteractor{


    private String api_id="188618e2-cc65-4c5b-acbc-0579d1a2c92d";
    ArrayList<Transaction> transactions=new ArrayList<>();


    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
