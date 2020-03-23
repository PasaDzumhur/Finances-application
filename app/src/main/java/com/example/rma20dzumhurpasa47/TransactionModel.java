package com.example.rma20dzumhurpasa47;

import android.icu.text.DateFormat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

public class TransactionModel {
    private static ArrayList<Transaction> trans = new ArrayList<Transaction>() {
        {
            String date = "15/2/2020";
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d = simpleDate.parse(date);



            add(new Transaction(new Date(System.currentTimeMillis()),20000,"prva", Transaction.Type.INDIVIDUALINCOME,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(new Date(System.currentTimeMillis()),30000,"druga", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(d,20000,"treca", Transaction.Type.PURCHASE,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(d,20000,"cetvrta", Transaction.Type.REGULARINCOME,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(d,20000,"peta", Transaction.Type.REGULARPAYMENT,"bezze",
                    20,new Date(System.currentTimeMillis())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    };

    public static ArrayList<Transaction> getTrans() {
        return trans;
    }
}
