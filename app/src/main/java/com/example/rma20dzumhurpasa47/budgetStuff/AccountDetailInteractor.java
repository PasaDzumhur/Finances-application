package com.example.rma20dzumhurpasa47.budgetStuff;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rma20dzumhurpasa47.data.Account;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.rma20dzumhurpasa47.list.TransactionListInteractor.convertStreamToString;

public class AccountDetailInteractor extends AsyncTask<Boolean,Integer,Account> implements IAccountDetailInteractor {

    private String api_id="188618e2-cc65-4c5b-acbc-0579d1a2c92d";
    private accountUpdate caller;
    private Account newAccount = null;

    public interface accountUpdate{
        public void accountUpdated(Account account);
    }

    public AccountDetailInteractor(accountUpdate caller) {
        this.caller = caller;
        execute(false);
    }
    public AccountDetailInteractor(Account newAccount) {
        //this.caller = caller;
        caller = null;
        this.newAccount=newAccount;
        execute(true);
    }
    private String accountToJson (Account account){
        String json ="{\"budget\": "+(int)account.getBudget()+",\"totalLimit\": "
                +(int)account.getTotalLimit()+",\"monthLimit\": "
                +(int)account.getMonthLimit()+"}";
        return json;
    }

    //String action = null;
    @Override
    protected Account doInBackground(Boolean... booleans) {
        String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id;
        try {
            if(!booleans[0]) {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(in);
                JSONObject jo = new JSONObject(result);
                double budget = jo.getInt("budget");
                double totalLimit = jo.getInt("totalLimit");
                double monthlyLimit = jo.getInt("monthLimit");
                Account account = new Account(budget, totalLimit, monthlyLimit);
                return account;
            }else if(booleans[0]){
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                String json = accountToJson(newAccount);

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


            }
            //JSONArray results = jo.getJSONArray("account");
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Account account) {
        super.onPostExecute(account);
        if(caller!=null) caller.accountUpdated(account);
    }
}
