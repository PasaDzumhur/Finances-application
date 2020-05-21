package com.example.rma20dzumhurpasa47.detail;


import android.os.AsyncTask;
import android.util.Log;


import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.list.TransactionListInteractor;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class TransactionDetailInteractor extends AsyncTask<Boolean,Integer,Void> implements ITransactionDetailInteractor {
    private String api_id="188618e2-cc65-4c5b-acbc-0579d1a2c92d";
    private Transaction selectedTransaction;
    private TransactionUpdateDone caller;
    private boolean save=false;
    private boolean delete=false;
    private boolean add=false;



    private static String transactionToJSON(Transaction transaction){
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String json = "{";
        //String idJson = "\"id\": "+transaction.getId()+",";
        String dateJson = "\"date\": \""+simpleDate.format(transaction.getDate())+"\",";
        String titleJson = "\"title\": \"" + transaction.getTitle()+"\",";
        String amountJson = "\"amount\": "+(int)transaction.getAmount()+",";
        String itemDescription = "\"itemDescription\": ";
        if(transaction.getItemDescription()!=null) itemDescription = itemDescription + "\""+transaction.getItemDescription()+"\",";
        else itemDescription = itemDescription + "null,";
        String interval = "";
        if(transaction.getTransactionInterval()==0) interval=interval+"null";
        else interval = interval+transaction.getTransactionInterval();
        String transactionInterval = "\"transactionInterval\": "+ interval+",";
        String endDate = "\"endDate\": ";
        if(transaction.getEndDate()!=null) endDate = endDate + "\""+simpleDate.format(transaction.getEndDate())+"\",";
        else endDate = endDate + "null,";
        //String AccountId = "\"AccountId: 21,";
        String transactionTypeId = "\"TransactionTypeId\": "+ Transaction.Type.getID(transaction.getType())+"}";
        json = json+dateJson+titleJson+amountJson+itemDescription+transactionInterval+endDate+transactionTypeId;


        return json;
    }


    public TransactionDetailInteractor(Transaction selectedTransaction, TransactionUpdateDone caller,boolean delete,boolean add, boolean save) {
        this.selectedTransaction = selectedTransaction;
        this.caller = caller;
        this.save=save;
        this.add=add;
        this.delete=delete;
        execute(delete,add,save);

    }

    public interface TransactionUpdateDone{
        public void onDone();
    }


    @Override
    protected Void doInBackground(Boolean... booleans) {


        if(booleans[0]) {
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions/"+selectedTransaction.getId();
            try{
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = TransactionListInteractor.convertStreamToString(in);
                JSONObject jsonObject = new JSONObject(result);
                String res = jsonObject.getString("success");
                Log.e("res: ",res);
                in.close();
                urlConnection.disconnect();
            }catch (Exception e){

            }
        }
        if(booleans[1]){
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions";
            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                String json = transactionToJSON(selectedTransaction);

                Log.e("json: ",json);



                try(OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    Log.e("RESPONSE",response.toString());
                }

            }catch (Exception e){

            }


        }
        if(booleans[2]){
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions/"+selectedTransaction.getId();
            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                String json = transactionToJSON(selectedTransaction);

                Log.e("json: ",json);



                try(OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    Log.e("RESPONSE",response.toString());
                }catch(Exception e){
                    throw e;
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        caller.onDone();
    }
}
