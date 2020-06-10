package com.example.rma20dzumhurpasa47.list;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.detail.TransactionDetailInteractor;
import com.example.rma20dzumhurpasa47.util.TransactionContentProvider;
import com.example.rma20dzumhurpasa47.util.TransactionDBOpeHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionListInteractor extends AsyncTask<String,Integer,Void> implements ITransactionListInteractor {

    private String api_id="188618e2-cc65-4c5b-acbc-0579d1a2c92d";
    ArrayList<Transaction> transactions=new ArrayList<>();
    private TransactionSearchDone caller;
    private String sort ;
    private Context context;
    private TransactionDBOpeHelper transactionDBOpeHelper;


    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }
    private static Transaction.Type getTypeFromId(int id){
        if(id==1) return Transaction.Type.REGULARPAYMENT;
        else if(id==2) return Transaction.Type.REGULARINCOME;
        else if(id==3) return Transaction.Type.PURCHASE;
        else if(id==4) return Transaction.Type.INDIVIDUALINCOME;
        else if(id==5) return Transaction.Type.INDIVIDUALPAYMENT;
        return null;
    }

    public TransactionListInteractor(TransactionSearchDone caller, String sort, Context context) {
        this.sort=sort;
        execute(sort);
        this.caller=caller;
        this.context = context;
        transactionDBOpeHelper = new TransactionDBOpeHelper(context);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(String... strings) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if( cm.getActiveNetworkInfo() != null) {
            {
                ArrayList<Transaction> reserves = new ArrayList<>();
                ContentResolver cr = context.getApplicationContext().getContentResolver();
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Uri transactionURI = Uri.parse("content://rma.provider.transactionReserves/elements");
                Cursor cursor = cr.query(transactionURI, null, null, null);
                if (cursor != null && cursor.moveToFirst()!=false) {
                    //cursor.moveToFirst();
                    do {
                        int idPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_ID);
                        int titlePos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_TITLE);
                        int amountPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_AMOUNT);
                        int datePos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_DATE);
                        int endDatePos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_END_DATE);
                        int itemDescriptionPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_ITEM_DESCRIPTION);
                        int transactionIntervalPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_INTERVAL);
                        int transactionTypeIdPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_TYPE_ID);

                        int id = cursor.getInt(idPos);
                        String title = cursor.getString(titlePos);
                        int amount = cursor.getInt(amountPos);
                        String dateHelp = cursor.getString(datePos);
                        Transaction.Type type = getTypeFromId(cursor.getInt(transactionTypeIdPos));
                        String itemDescription = cursor.getString(itemDescriptionPos);
                        Date endDate = null;
                        int transactionInterval = 0;
                        try {
                            Date date = simpleDate.parse(dateHelp);
                            if (type == Transaction.Type.REGULARINCOME || type == Transaction.Type.REGULARPAYMENT) {
                                endDate = simpleDate.parse(cursor.getString(endDatePos));
                                transactionInterval = cursor.getInt(transactionIntervalPos);
                            }

                            reserves.add(new Transaction(date, amount, title, type, itemDescription, transactionInterval, endDate));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } while (cursor.moveToNext());

                    for(Transaction reserve : reserves){
                        if(reserve.getId()==-1){
                            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions";
                            try {
                                URL url = new URL(url1);
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("POST");
                                urlConnection.setRequestProperty("Content-Type", "application/json");
                                urlConnection.setRequestProperty("Accept", "application/json");
                                urlConnection.setDoOutput(true);
                                urlConnection.setDoInput(true);
                                String json = TransactionDetailInteractor.transactionToJSON(reserve);

                                Log.e("json: ", json);


                                try (OutputStream os = urlConnection.getOutputStream()) {
                                    byte[] input = json.getBytes("utf-8");
                                    os.write(input, 0, input.length);
                                }

                                try (BufferedReader br = new BufferedReader(
                                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                                    StringBuilder response = new StringBuilder();
                                    String responseLine = null;
                                    while ((responseLine = br.readLine()) != null) {
                                        response.append(responseLine.trim());
                                    }
                                    Log.e("RESPONSE", response.toString());
                                }

                            } catch (Exception e) {

                            }
                        }else if(reserve.getId()<1){
                            reserve.setId(-reserve.getId());
                            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions/" + reserve.getId();
                            try {
                                URL url = new URL(url1);
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("POST");
                                urlConnection.setRequestProperty("Content-Type", "application/json");
                                urlConnection.setRequestProperty("Accept", "application/json");
                                urlConnection.setDoOutput(true);
                                urlConnection.setDoInput(true);
                                String json = TransactionDetailInteractor.transactionToJSON(reserve);

                                Log.e("json: ", json);


                                try (OutputStream os = urlConnection.getOutputStream()) {
                                    byte[] input = json.getBytes("utf-8");
                                    os.write(input, 0, input.length);
                                }

                                try (BufferedReader br = new BufferedReader(
                                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                                    StringBuilder response = new StringBuilder();
                                    String responseLine = null;
                                    while ((responseLine = br.readLine()) != null) {
                                        response.append(responseLine.trim());
                                    }
                                    Log.e("RESPONSE", response.toString());
                                } catch (Exception e) {
                                    throw e;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions/" + reserve.getId();
                            try {
                                URL url = new URL(url1);
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("DELETE");
                                urlConnection.setDoOutput(true);
                                urlConnection.connect();
                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                String result = TransactionListInteractor.convertStreamToString(in);
                                JSONObject jsonObject = new JSONObject(result);
                                String res = jsonObject.getString("success");
                                Log.e("res: ", res);
                                in.close();
                                urlConnection.disconnect();
                            } catch (Exception e) {

                            }
                        }
                    }
                }
                Uri uri = Uri.parse("content://rma.provider.transactionReserves/elements");
                ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
                contentResolver.delete(uri, null, null);

            }







            //String query=null;
            Uri uri = Uri.parse("content://rma.provider.transactions/elements");
            ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
            contentResolver.delete(uri, null, null);

            for (int query = 0; ; query++) {


            /*try {
                query = URLEncoder.encode(pom, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
                String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions/filter?sort=" + strings[0] + "&page=" + query;
                try {
                    URL url = new URL(url1);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String result = convertStreamToString(in);
                    JSONObject jo = new JSONObject(result);
                    JSONArray results = jo.getJSONArray("transactions");
                    if (results.length() == 0) {
                        //System.out.println("Gotovo----------------------------------------------------------------------------------------------------------");
                        break;
                    }

                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    //ako bude kakvih problema ovaj koristit yyyy-MM-dd'T'HH:mm:ss.SSS

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject transaction = results.getJSONObject(i);
                        String title = transaction.getString("title");
                        String dateString = transaction.getString("date");

                        Date startDate = null;
                        if (dateString != null) startDate = simpleDate.parse(dateString);
                        String dateString2 = transaction.getString("endDate");
                        //System.out.println(dateString2);
                        Date endDate = null;
                        if (dateString2 != null && !dateString2.equals("null"))
                            endDate = simpleDate.parse(dateString2);
                        String itemDescription = transaction.getString("itemDescription");
                        //Integer transactionInterval = transaction.getInt("transactionInterval");
                        int transactionInterval = 0;
                        String intervalHelp = transaction.getString("transactionInterval");
                        if (!intervalHelp.equals("null"))
                            transactionInterval = Integer.parseInt(intervalHelp);
                        Double amount = transaction.getDouble("amount");
                        Transaction.Type type = getTypeFromId(transaction.getInt("TransactionTypeId"));
                        int id = transaction.getInt("id");
                        Transaction newTransaction = new Transaction(startDate, amount, title, type, itemDescription, transactionInterval, endDate, id);
                        transactions.add(newTransaction);

                        ContentResolver cr = context.getApplicationContext().getContentResolver();
                        Uri transactionURI = Uri.parse("content://rma.provider.transactions/elements");
                        ContentValues values = new ContentValues();
                        values.put(TransactionDBOpeHelper.TRANSACTION_ID, newTransaction.getId());
                        values.put(TransactionDBOpeHelper.TRANSACTION_TITLE, newTransaction.getTitle());
                        values.put(TransactionDBOpeHelper.TRANSACTION_AMOUNT, newTransaction.getAmount());
                        values.put(TransactionDBOpeHelper.TRANSACTION_TYPE_ID, transaction.getInt("TransactionTypeId"));
                        values.put(TransactionDBOpeHelper.TRANSACTION_DATE, simpleDate.format(newTransaction.getDate()));
                        values.put(TransactionDBOpeHelper.TRANSACTION_ITEM_DESCRIPTION, newTransaction.getItemDescription());
                        String pom = null;
                        if (newTransaction.getEndDate() != null)
                            pom = simpleDate.format(newTransaction.getEndDate());
                        values.put(TransactionDBOpeHelper.TRANSACTION_END_DATE, pom);
                        values.put(TransactionDBOpeHelper.TRANSACTION_INTERVAL, newTransaction.getTransactionInterval());
                        cr.insert(transactionURI, values);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        /*
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            transactions.add(new Transaction(simpleDate.parse("16/3/2020"),2500,"4aa", Transaction.Type.REGULARINCOME,"4",
                    20,simpleDate.parse("15/12/2020"),1));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        }else{


        }
        return null;
    }
    @Override
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        if(!MainActivity.connectivity){
            ContentResolver cr = context.getApplicationContext().getContentResolver();
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Uri transactionURI = Uri.parse("content://rma.provider.transactions/elements");
            Cursor cursor = cr.query(transactionURI,null,null,null);
            if(cursor!=null){
                cursor.moveToFirst();
                do{
                    int idPos=cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_ID);
                    int titlePos=cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_TITLE);
                    int amountPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_AMOUNT);
                    int datePos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_DATE);
                    int endDatePos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_END_DATE);
                    int itemDescriptionPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_ITEM_DESCRIPTION);
                    int transactionIntervalPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_INTERVAL);
                    int transactionTypeIdPos = cursor.getColumnIndexOrThrow(TransactionDBOpeHelper.TRANSACTION_TYPE_ID);

                    int id = cursor.getInt(idPos);
                    String title = cursor.getString(titlePos);
                    int amount = cursor.getInt(amountPos);
                    String dateHelp = cursor.getString(datePos);
                    Transaction.Type type = getTypeFromId(cursor.getInt(transactionTypeIdPos));
                    String itemDescription = cursor.getString(itemDescriptionPos);
                    Date endDate= null;
                    int transactionInterval = 0;
                    try {
                        Date date = simpleDate.parse(dateHelp);
                        if(type == Transaction.Type.REGULARINCOME || type == Transaction.Type.REGULARPAYMENT){
                            endDate=simpleDate.parse(cursor.getString(endDatePos));
                            transactionInterval=cursor.getInt(transactionIntervalPos);
                        }

                        transactions.add(new Transaction(date,amount,title,type,itemDescription,transactionInterval,endDate));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }while (cursor.moveToNext());
            }

        }

        ArrayList<Transaction> regularTransactions = new ArrayList<>();

        for(int i=0; i<transactions.size(); i++){
            Transaction transaction = transactions.get(i);
            if(transaction.getType() == Transaction.Type.REGULARINCOME || transaction.getType() == Transaction.Type.REGULARPAYMENT){

                Calendar calendar= Calendar.getInstance();
                calendar.setTime(transaction.getDate());
                calendar.add(Calendar.DAY_OF_YEAR,transaction.getTransactionInterval());
                while(calendar.getTime().before(transaction.getEndDate())){
                    //calendar.add(Calendar.DAY_OF_YEAR,transaction.getTransactionInterval());
                    regularTransactions.add(new Transaction(calendar.getTime(),transaction.getAmount(),transaction.getTitle(),transaction.getType(),transaction.getItemDescription(),
                            transaction.getTransactionInterval(),transaction.getEndDate(),transaction.getId()));
                    calendar.add(Calendar.DAY_OF_YEAR,transaction.getTransactionInterval());

                }




            }else continue;
        }
        //System.out.println("------------------------------------------------------------"+regularTransactions.size());
        for(Transaction transaction : regularTransactions){

            for(int i=0; i<transactions.size(); i++){
                //Calendar calendar = Calendar.getInstance();
                //calendar.setTime(transaction.getDate());

                if(sort.equals("date.asc")){
                    if(transaction.getDate().before(transactions.get(i).getDate())){
                        transactions.add(i,transaction);
                        break;
                    }
                }else if(sort.equals("date.desc")){
                    if(transaction.getDate().after(transactions.get(i).getDate())){
                        transactions.add(i,transaction);
                        break;
                    }
                }else {
                    if(transaction.getId()==transactions.get(i).getId()){
                        transactions.add(i,transaction);
                        break;
                    }
                }
            }


        }


        //Log.e("Transakcija ima: ", ""+transactions.size());
        caller.onDone(transactions);
    }

    public interface TransactionSearchDone {

        public void onDone(ArrayList<Transaction> results);

    }






















    /*
    private Calendar pom=Calendar.getInstance();
    @Override
    public ArrayList<Transaction> get() {

        String string= MainActivity.getFilter();
        if(string.equalsIgnoreCase("PURCHASE")) {
            return getPurchase();
        }
        else if(string.equals("INDIVIDUALPAYMENT")) {
            return getIndividualPayment();
        }
        else if(string.equalsIgnoreCase("REGULARPAYMENT")) {
            return getRegularPayment();
        }
        else if(string.equalsIgnoreCase("INDIVIDUALINCOME")) {
            return getIndividualIncome();
        }
        else if(string.equalsIgnoreCase("REGULARINCOME")) {
            return getRegularIncome();
        }
        else {
            return getClassic();
        }
    }

    public ArrayList<Transaction> getClassic() {


        ArrayList<Transaction> transfilter=new ArrayList<>();
        for(Transaction t : TransactionModel.trans){
            if(t.getType().equals(Transaction.Type.INDIVIDUALINCOME) || t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT) || t.getType().equals(Transaction.Type.PURCHASE)){
                pom.setTime(t.getDate());
                if(pom.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && pom.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) {
                    transfilter.add(t);
                }
            }else{
                if(pom.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && pom.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)) transfilter.add(t);
                else{
                    Calendar pom2=Calendar.getInstance();
                    pom2.setTime(t.getEndDate());
                    if(pom.get(Calendar.MONTH)!=pom2.get(Calendar.MONTH) || pom.get(Calendar.YEAR)!=pom2.get(Calendar.YEAR)){
                        while (true){
                            pom.add(Calendar.MONTH,1);
                            if(pom.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && pom.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)){
                                transfilter.add(t);
                                break;
                            }
                            if(pom.get(Calendar.MONTH)!=pom2.get(Calendar.MONTH) || pom.get(Calendar.YEAR)!=pom2.get(Calendar.YEAR)) break;
                        }
                    }
                }
            }
        }
        return transfilter;

    }

    @Override
    public ArrayList<Transaction> getPurchase() {

        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter){
            if (t.getType().equals(Transaction.Type.PURCHASE)) transfilter.add(t);
        }
        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getIndividualPayment() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.INDIVIDUALPAYMENT)) transfilter.add(t);
        }

        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getRegularPayment() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.REGULARPAYMENT)) transfilter.add(t);
        }

        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getIndividualIncome() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.INDIVIDUALINCOME)) transfilter.add(t);
        }

        return transfilter;
    }

    @Override
    public ArrayList<Transaction> getRegularIncome() {
        ArrayList<Transaction> transfilter=new ArrayList<>();
        ArrayList<Transaction> monthFilter=getClassic();
        for(Transaction t : monthFilter) {
            if (t.getType().equals(Transaction.Type.REGULARINCOME)) transfilter.add(t);
        }

        return transfilter;
    }*/


}
