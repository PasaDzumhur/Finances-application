package com.example.rma20dzumhurpasa47;


import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Transaction implements Comparable<Transaction> {
    private Date date;
    private double amount;
    private String title;



    public enum Type{
        INDIVIDUALPAYMENT, REGULARPAYMENT, PURCHASE, INDIVIDUALINCOME, REGULARINCOME;

        @Override
        public String toString() {
            if(this.equals(INDIVIDUALPAYMENT)) return "INDIVIDUALPAYMENT";
            else if(this.equals(REGULARPAYMENT)) return "REGULARPAYMENT";
            else if(this.equals(PURCHASE)) return "PURCHASE";
            else if(this.equals(INDIVIDUALINCOME)) return "INDIVIDUALINCOME";
            else if(this.equals(REGULARINCOME)) return "REGULARINCOME";
            else return "Kretenu ne mozes ovako";
        }
        public static Type gimmeType(String tip){
            if(tip.equalsIgnoreCase("INDIVIDUALPAYMENT")) return INDIVIDUALPAYMENT;
            else if(tip.equalsIgnoreCase("REGULARPAYMENT")) return REGULARPAYMENT;
            else if(tip.equalsIgnoreCase("PURCHASE")) return PURCHASE;
            else if(tip.equalsIgnoreCase("INDIVIDUALINCOME")) return INDIVIDUALINCOME;
            else if(tip.equalsIgnoreCase("REGULARINCOME")) return REGULARINCOME;

            throw new IllegalArgumentException();
        }

    };
    private Type type;
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;

    public static boolean provjeraDuzine(String title) {
        if (title.length() > 14 || title.length() < 3) return false;
        return true;
    }

    public Transaction(Date date, double amount, String title, Type type, String itemDescription, int transactionInterval, Date endDate) {
        if(!provjeraDuzine(title)) throw new IllegalArgumentException("Ne valja title");
        if(type.equals(Type.REGULARINCOME) || type.equals(Type.INDIVIDUALINCOME)) itemDescription=null;
        if(!(type.equals(Type.REGULARINCOME) || type.equals(Type.REGULARPAYMENT))){
            transactionInterval=0;
            endDate=null;
        }
        if(endDate!=null){
            if(endDate.before(date)) throw new IllegalArgumentException("The end date can't be before de start date!");
        }
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.type = type;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(!provjeraDuzine(title)) throw new IllegalArgumentException("ne valja title");
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(int transactionInterval) {
        this.transactionInterval = transactionInterval;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int compareTo(Transaction o) {
        return Integer.compare(this.type.ordinal(),o.getType().ordinal());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                transactionInterval == that.transactionInterval &&
                Objects.equals(date, that.date) &&
                Objects.equals(title, that.title) &&
                type == that.type &&
                Objects.equals(itemDescription, that.itemDescription) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, title, type, itemDescription, transactionInterval, endDate);
    }


    public static double obradiRegular(Transaction transaction, Calendar trenutniMjesec) throws ParseException {
        double sum=0;
        if(transaction.getType().equals(Type.REGULARINCOME) || transaction.getType().equals(Type.REGULARPAYMENT)){


            Calendar calendar=Calendar.getInstance();

            trenutniMjesec.set(Calendar.DAY_OF_MONTH,1);
            trenutniMjesec.add(Calendar.MONTH,1);
            trenutniMjesec.add(Calendar.DAY_OF_MONTH,-1);
            Date pom=trenutniMjesec.getTime();

            if(transaction.getEndDate().before(pom)) pom=transaction.getEndDate();
            int daysDuration = Account.getDifferenceDays(transaction.getDate(),pom);
            int numberOfCalcs=daysDuration/transaction.getTransactionInterval();
            sum=sum+numberOfCalcs*transaction.getAmount();
            //if(transaction.getType().equals(Type.REGULARPAYMENT)) sum*=-1;


        }else{
            sum=transaction.getAmount();

        }
        return sum;
    }

}
