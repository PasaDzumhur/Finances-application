package com.example.rma20dzumhurpasa47;

import android.icu.text.DateFormat;


import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

public class TransactionModel {
    public static ArrayList<Transaction> trans = new ArrayList<Transaction>() {
        {
            String date = "15/2/2020";
            String date1="2/2/2020";
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            try {

                Date d = simpleDate.parse(date);

                //d=simpleDate.parse("02/03/2020");

            add(new Transaction(simpleDate.parse("02/03/2020"),20000,"1aa", Transaction.Type.INDIVIDUALINCOME,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("2/2/2020"),30000,"2aa", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("1/4/2020"),20000,"3aa", Transaction.Type.PURCHASE,"3",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("16/3/2020"),20000,"4aa", Transaction.Type.REGULARINCOME,"4",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("2/4/2020"),20000,"5aa", Transaction.Type.REGULARPAYMENT,"5",
                    20,new Date(System.currentTimeMillis())));


                add(new Transaction(simpleDate.parse("12/2/2020"),20000,"6aa", Transaction.Type.INDIVIDUALINCOME,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("22/4/2020"),30000,"7aa", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("15/3/2020"),20000,"8aa", Transaction.Type.PURCHASE,"8",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("5/4/2020"),20000,"9aa", Transaction.Type.REGULARINCOME,"9",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("22/2/2020"),20000,"10a", Transaction.Type.REGULARPAYMENT,"10",
                        20,new Date(System.currentTimeMillis())));



                add(new Transaction(simpleDate.parse("30/3/2020"),20000,"11a", Transaction.Type.INDIVIDUALINCOME,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("28/2/2020"),30000,"12a", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("30/3/2020"),20000,"13a", Transaction.Type.PURCHASE,"13",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("25/2/2020"),20000,"14a", Transaction.Type.REGULARINCOME,"14",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("19/4/2020"),20000,"15a", Transaction.Type.REGULARPAYMENT,"15",
                        20,new Date(System.currentTimeMillis())));

                add(new Transaction(simpleDate.parse("20/2/2020"),20000,"16a", Transaction.Type.INDIVIDUALINCOME,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("11/3/2020"),30000,"17a", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("29/4/2020"),20000,"18a", Transaction.Type.PURCHASE,"18",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("3/4/2020"),20000,"19a", Transaction.Type.REGULARINCOME,"19",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("6/4/2020"),20000,"20a", Transaction.Type.REGULARPAYMENT,"20",
                        20,new Date(System.currentTimeMillis())));


            } catch (ParseException e) {
                System.out.println("greska");
            }

        }


    };
    public static ArrayList<Transaction> ostatak=new ArrayList<>();

    public static ArrayList<Transaction> getTrans() {return trans;}
}
