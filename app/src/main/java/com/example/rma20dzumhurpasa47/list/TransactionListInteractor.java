package com.example.rma20dzumhurpasa47.list;

import android.os.AsyncTask;

import com.example.rma20dzumhurpasa47.data.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionListInteractor extends AsyncTask<String,Integer,Void> implements ITransactionListInteractor {

    private String api_id="188618e2-cc65-4c5b-acbc-0579d1a2c92d";
    ArrayList<Transaction> transactions=new ArrayList<>();
    private TransactionSearchDone caller;

    public String convertStreamToString(InputStream is) {
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

    public TransactionListInteractor(TransactionSearchDone caller) {
        execute("");
        this.caller=caller;
    }

    @Override
    protected Void doInBackground(String... strings) {
        //String query=null;
        for(int query=0; ; query++) {


            /*try {
                query = URLEncoder.encode(pom, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            String url1 = "http://rma20-app-rmaws.apps.us-west-1.starter.openshift-online.com/account/" + api_id + "/transactions?page=" + query;
            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(in);
                JSONObject jo = new JSONObject(result);
                JSONArray results = jo.getJSONArray("transactions");
                if(results.length()==0) {
                    //System.out.println("Gotovo----------------------------------------------------------------------------------------------------------");
                    break;
                }

                SimpleDateFormat simpleDate = new SimpleDateFormat("dd-Mm-yyyy");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject transaction = results.getJSONObject(i);
                    String title = transaction.getString("title");
                    String dateString = transaction.getString("date");

                    Date  startDate =null;
                    if(dateString!=null)  startDate = simpleDate.parse(dateString);
                    String dateString2 = transaction.getString("endDate");
                    Date endDate = null;
                    if(dateString2!=null) endDate = simpleDate.parse(dateString);
                    String itemDescription = transaction.getString("itemDescription");
                    //Integer transactionInterval = transaction.getInt("transactionInterval");
                    int transactionInterval=0;
                    String intervalHelp = transaction.getString("transactionInterval");
                    if(!intervalHelp.equals("null")) transactionInterval = Integer.parseInt(intervalHelp);
                    Double amount = transaction.getDouble("amount");
                    Transaction.Type type = getTypeFromId(transaction.getInt("TransactionTypeId"));
                    transactions.add(new Transaction(startDate,amount,title,type,itemDescription,transactionInterval,endDate));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }
    @Override
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
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
