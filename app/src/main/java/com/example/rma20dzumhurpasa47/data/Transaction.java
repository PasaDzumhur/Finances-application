package com.example.rma20dzumhurpasa47.data;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class Transaction implements Parcelable {
    private Date date;
    private double amount;
    private String title;
    private int id=-1;



    public enum Type{
        //INDIVIDUALPAYMENT, REGULARPAYMENT, PURCHASE, INDIVIDUALINCOME, REGULARINCOME;
        REGULARPAYMENT,REGULARINCOME,PURCHASE,INDIVIDUALINCOME,INDIVIDUALPAYMENT;
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

            return null;
        }
        public static int getID(Type type){
            if(type == REGULARPAYMENT) return 1;
            else if(type == REGULARINCOME) return 2;
            else if (type == PURCHASE) return 3;
            else if (type == INDIVIDUALINCOME) return 4;
            else if (type == INDIVIDUALPAYMENT) return 5;
            return 0;
        }




    };
    private Type type;
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;

    protected Transaction(Parcel in) {
        date = new Date();
        long pom=in.readLong();
        date.setTime(pom);
        amount = in.readDouble();
        title = in.readString();
        itemDescription = in.readString();
        transactionInterval = in.readInt();
        endDate=new Date();
        pom=in.readLong();
        endDate.setTime(pom);
        id=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeDouble(amount);
        dest.writeString(title);
        dest.writeSerializable(type);
        dest.writeString(itemDescription);
        dest.writeInt(transactionInterval);
        dest.writeLong(endDate.getTime());
        dest.writeInt(id);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public static boolean provjeraDuzine(String title) {
        //System.out.println(title +" " + title.length());
        if (title.length() > 16 || title.length() < 3) return false;
        return true;
    }

    public Transaction(Date date, double amount, String title, Type type, String itemDescription, int transactionInterval, Date endDate, int id) {
        if(!provjeraDuzine(title)) throw new IllegalArgumentException("Ne valja title");
        if(type.equals(Type.REGULARINCOME) || type.equals(Type.INDIVIDUALINCOME)) itemDescription=null;
        if(!(type.equals(Type.REGULARINCOME) || type.equals(Type.REGULARPAYMENT))){
            transactionInterval=0;
            endDate=null;
        }
        if(endDate!=null){
            if(endDate.before(date)) {
                System.out.println("date: "+date+ " endDate: "+endDate+ " ----------");
                throw new IllegalArgumentException("The end date can't be before de start date!");
            }
        }
        this.date = date;
        this.amount = amount;
        this.title = title;
        this.type = type;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
        this.id=id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
    /*
    @Override
    public int compareTo(Transaction o) {
        return Integer.compare(this.type.ordinal(),o.getType().ordinal());
    }*/


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




            trenutniMjesec.set(Calendar.DAY_OF_MONTH,1);
            trenutniMjesec.add(Calendar.MONTH,1);
            trenutniMjesec.add(Calendar.DAY_OF_MONTH,-1);
            Date pom=trenutniMjesec.getTime();

            if(transaction.getEndDate().before(pom)) pom=transaction.getEndDate();
            int daysDuration = Account.getDifferenceDays(transaction.getDate(),pom);
            int numberOfCalcs=daysDuration/transaction.getTransactionInterval();
            sum=sum+numberOfCalcs*transaction.getAmount();


        }else{
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(transaction.getDate());
            if(trenutniMjesec.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)) sum=transaction.getAmount();

        }
        return sum;
    }


    public double monthlyAmount(int month){
        double sum=0;
        if(getType().equals(Type.REGULARINCOME) || getType().equals(Type.REGULARPAYMENT)){
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.MONTH,month);

            Calendar pom1=Calendar.getInstance();
            Calendar pom2=Calendar.getInstance();
            pom1.setTime(date);
            pom2.setTime(endDate);
            if(!(pom1.get(Calendar.MONTH)<=month && pom2.get(Calendar.MONTH)>=month)) return 0;
            while (pom1.get(Calendar.MONTH)!=month) pom1.add(Calendar.DAY_OF_MONTH,transactionInterval);
            //while (pom2.get(Calendar.MONTH)!=month) pom1.add(Calendar.DAY_OF_MONTH,-transactionInterval);
            if(pom2.get(Calendar.MONTH)!=month){
                int razlika=pom2.get(Calendar.MONTH)-month;
                pom1.add(Calendar.MONTH,razlika);
            }
            int daysDuration = Account.getDifferenceDays(pom1.getTime(),pom2.getTime());
            int numberOfCalcs=daysDuration/transactionInterval;
            sum=sum+numberOfCalcs*amount;

        }else{
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            if(calendar.get(Calendar.MONTH)==month) sum=getAmount();

        }
        return sum;
    }

    public double getRealAmount(){
        //Pair<Double,Double>
        double sum = 0;
        int prefiks = -1;
        if(getType()==Type.REGULARINCOME || getType()==Type.INDIVIDUALINCOME) prefiks = 1;
        if(!(getType().equals(Type.REGULARINCOME) || getType().equals(Type.REGULARPAYMENT))){
            return prefiks * getAmount();
        }else {
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(getDate());

            while(calendar.getTime().before(getEndDate())){
                sum = sum + getAmount();

                calendar.add(Calendar.DAY_OF_YEAR,getTransactionInterval());
            }
        }

        return prefiks * sum;
    }

}
