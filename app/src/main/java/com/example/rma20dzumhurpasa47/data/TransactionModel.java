package com.example.rma20dzumhurpasa47.data;


import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;

public class TransactionModel {
    public static ArrayList<Transaction> trans = new ArrayList<Transaction>() {
        {
            String date = "15/2/2020";
            String date1="2/2/2020";
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            try {

                Date d = simpleDate.parse(date);


            add(new Transaction(simpleDate.parse("02/03/2020"),200,"1aa", Transaction.Type.INDIVIDUALINCOME,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("2/2/2020"),3030,"2aa", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("1/4/2020"),20500,"3aa", Transaction.Type.PURCHASE,"3",
                    20,new Date(System.currentTimeMillis())));
            add(new Transaction(simpleDate.parse("16/3/2020"),2500,"4aa", Transaction.Type.REGULARINCOME,"4",
                    20,simpleDate.parse("15/12/2020")));
            add(new Transaction(simpleDate.parse("2/4/2020"),2400,"5aa", Transaction.Type.REGULARPAYMENT,"5",
                    20,simpleDate.parse("15/10/2020")));


                add(new Transaction(simpleDate.parse("12/2/2020"),2150,"6aa", Transaction.Type.INDIVIDUALINCOME,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("22/4/2020"),345,"7aa", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("15/3/2020"),23,"8aa", Transaction.Type.PURCHASE,"8",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("5/4/2020"),253,"9aa", Transaction.Type.REGULARINCOME,"9",
                        20,simpleDate.parse("15/5/2020")));
                add(new Transaction(simpleDate.parse("22/2/2020"),277,"10a", Transaction.Type.REGULARPAYMENT,"10",
                        20,simpleDate.parse("5/4/2020")));



                add(new Transaction(simpleDate.parse("30/3/2020"),860,"11a", Transaction.Type.INDIVIDUALINCOME,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("28/2/2020"),3640,"12a", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("30/3/2020"),8650,"13a", Transaction.Type.PURCHASE,"13",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("25/2/2020"),930,"14a", Transaction.Type.REGULARINCOME,"14",
                        20,simpleDate.parse("15/3/2020")));
                add(new Transaction(simpleDate.parse("19/4/2020"),1000,"15a", Transaction.Type.REGULARPAYMENT,"15",
                        20,simpleDate.parse("31/12/2020")));

                add(new Transaction(simpleDate.parse("20/2/2020"),64000,"16a", Transaction.Type.INDIVIDUALINCOME,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("11/3/2020"),360,"17a", Transaction.Type.INDIVIDUALPAYMENT,"bezze",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("29/4/2020"),2030,"18a", Transaction.Type.PURCHASE,"18",
                        20,new Date(System.currentTimeMillis())));
                add(new Transaction(simpleDate.parse("3/4/2020"),2035,"19a", Transaction.Type.REGULARINCOME,"19",
                        20,simpleDate.parse("2/5/2020")));
                add(new Transaction(simpleDate.parse("6/4/2020"),200,"20a", Transaction.Type.REGULARPAYMENT,"20",
                        20,simpleDate.parse("20/5/2020")));


            } catch (ParseException e) {
                System.out.println("greska");
            }

        }



    };
    public static HashMap<String,Integer> slikice=new HashMap<String,Integer>(){
        {
            put("PURCHASE", R.drawable.pur);
            put("INDIVIDUALINCOME",R.drawable.indinc);
            put("INDIVIDUALPAYMENT",R.drawable.indpa);
            put("REGULARINCOME",R.drawable.regin);
            put("REGULARPAYMENT",R.drawable.download);
            put("GIMMEALL",R.drawable.unnamed);
        }
    };


    public static ArrayList<Transaction> ostatak=new ArrayList<>();

    public static ArrayList<Transaction> getTrans() {return trans;}
}
