package com.example.rma20dzumhurpasa47;

import java.util.Date;

public class Transaction {
    private Date date;
    private double amount;
    private String title;
    public enum Type{
        INDIVIDUALPAYMENT, REGULARPAYMENT, PURCHASE, INDIVIDUALINCOME, REGULARINCOME;
    };
    private Type type;
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;
    private boolean provjeraDuzine(String title){
        if(title.length()>14 || title.length()<3) return false;
        return true;
    }

    public Transaction(Date date, double amount, String title, Type type, String itemDescription, int transactionInterval, Date endDate) {
        if(!provjeraDuzine(title)) throw new IllegalArgumentException("Ne valja title");
        if(type.equals(Type.REGULARINCOME) || type.equals(Type.INDIVIDUALINCOME)) itemDescription=null;
        if(!(type.equals(Type.REGULARINCOME) || type.equals(Type.REGULARPAYMENT))){
            transactionInterval=0;
            endDate=null;
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
}
