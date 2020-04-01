package com.example.rma20dzumhurpasa47;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7;
    private Button btnSave,btnDelete;
    /*
    private ITransactionDetailPresenter presenter;

    public ITransactionDetailPresenter getPresenter(){
        if(presenter==null){
            presenter=new TransactionDetailPresenter(this);
        }
        return presenter;
    }*/

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_detail);
        /*getPresenter().create(getIntent().getSerializableExtra("date"),getIntent().getStringExtra("amount"),
                getIntent().getStringExtra("title"),getIntent().getStringExtra("type"),
                getIntent().getStringExtra("itemDescription"),getIntent().getStringExtra("transactionInterval"),
                getIntent().getStringExtra("endDate"));*/

        //Bundle extras = getIntent().getExtras();
        /*getPresenter().create((Date)extras.get("date"),(double)extras.get("amount"),(String)extras.get("title"),
                (Transaction.Type)extras.get("type"),(String)extras.get("itemDescription"),(int)extras.get("transactionInterval"),
                (Date)extras.get("endDate"));*/
        edit1=findViewById(R.id.edit1);
        edit2=findViewById(R.id.edit2);
        edit3=findViewById(R.id.edit3);
        edit4=findViewById(R.id.edit4);
        edit5=findViewById(R.id.edit5);
        edit6=findViewById(R.id.edit6);
        edit7=findViewById(R.id.edit7);
        edit1.setBackgroundColor(Color.RED);
        edit2.setBackgroundColor(Color.RED);
        edit3.setBackgroundColor(Color.RED);
        edit4.setBackgroundColor(Color.RED);
        edit5.setBackgroundColor(Color.RED);
        edit6.setBackgroundColor(Color.RED);
        edit7.setBackgroundColor(Color.RED);

        btnDelete=findViewById(R.id.btnDelete);
        btnDelete.setEnabled(false);
        btnSave=findViewById(R.id.btnSave);
        //final Transaction transaction=getPresenter().getTransaction();


        final SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");



        /*
        edit5.setText(formatter.format(transaction.getDate()));
        edit2.setText(""+transaction.getAmount());
        edit1.setText(transaction.getTitle());
        edit3.setText(transaction.getType().toString());
        edit4.setText(transaction.getItemDescription());
        edit6.setText(""+transaction.getTransactionInterval());
        if(transaction.getEndDate()==null) edit7.setText("");
        else edit7.setText(formatter.format(transaction.getEndDate()));*/
        edit5.setText("");
        edit2.setText("");
        edit1.setText("");
        edit3.setText("");
        edit4.setText("");
        edit6.setText("");
        edit7.setText("");
        boolean provjera1=false,provjera2=false,provjera3=false,provjera4=false,provjera5=false,provjera6=false,provjera7=false;




        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Transaction.provjeraDuzine(edit1.getText().toString())) {
                    edit1.setBackgroundColor(Color.GREEN);
                }

            }
        });

        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit2.setBackgroundColor(Color.GREEN);

            }
        });

        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit3.setBackgroundColor(Color.GREEN);

            }
        });

        edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit4.setBackgroundColor(Color.GREEN);

            }
        });

        edit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit5.setBackgroundColor(Color.GREEN);


            }
        });

        edit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit6.setBackgroundColor(Color.GREEN);

            }
        });

        edit7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit7.setBackgroundColor(Color.GREEN);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Date dateHelp=formatter.parse(edit5.getText().toString());
                    double amountHelp=Double.parseDouble(edit2.getText().toString());
                    String titleHelp=edit1.getText().toString();
                    Transaction.Type typeHelp= Transaction.Type.gimmeType(edit3.getText().toString());
                    String descriptionHelp=edit4.getText().toString();
                    int intervalHelp;
                    Date endDateHelp;
                    if(typeHelp.equals(Transaction.Type.REGULARPAYMENT) || typeHelp.equals(Transaction.Type.REGULARINCOME)){
                        intervalHelp=Integer.parseInt(edit6.getText().toString());
                        endDateHelp=formatter.parse(edit7.getText().toString());
                    }else{
                        intervalHelp=0;
                        endDateHelp=null;
                    }
                    final Transaction newTransaction=new Transaction(dateHelp,amountHelp,titleHelp,typeHelp,descriptionHelp,intervalHelp,endDateHelp);

                    ArrayList<Transaction> helpModel=new ArrayList<>();
                    helpModel.addAll(TransactionModel.trans);
                    helpModel.add(newTransaction);
                    Calendar helpCalendar=Calendar.getInstance();
                    helpCalendar.setTime(newTransaction.getDate());
                    if(MainActivity.account.testMonthlyLimit(helpModel,helpCalendar.get(Calendar.MONTH),MainActivity.account.getMonthLimit())){
                        AlertDialog alert = new AlertDialog.Builder(AddTransactionActivity.this).setTitle("Warning!!!!!!!").setMessage("Are you sure you want to go over the monthly limit?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TransactionModel.trans.add(newTransaction);
                                        finish();
                                    }
                                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                    }else{
                        TransactionModel.trans.add(newTransaction);
                        finish();
                    }




                } catch (Exception e) {
                    final AlertDialog alert=new AlertDialog.Builder(AddTransactionActivity.this).setTitle("Warning").setMessage("Invalid input!").
                            setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }

                /*
                try {
                    Transaction.Type pom= Transaction.Type.gimmeType(edit3.getText().toString());

                    Transaction newTransaction = new Transaction(formatter.parse(edit5.getText().toString()),Double.parseDouble(edit2.getText().toString()),edit1.getText().toString(),
                            pom,edit4.getText().toString(),Integer.parseInt(edit6.getText().toString()),formatter.parse(edit7.getText().toString()));
                    //if(newTransaction.equals(transaction)) finish();
                    //else{
                        //TransactionModel.trans.remove(transaction);
                        TransactionModel.trans.add(newTransaction);
                        finish();

                    //}
                } catch (ParseException e) {

                } catch (IllegalArgumentException r){

                }*/

            }
        });
        /*
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction pom=new Transaction(transaction.getDate(),transaction.getAmount(),transaction.getTitle(),transaction.getType(),transaction.getItemDescription(),transaction.getTransactionInterval(),transaction.getEndDate());
                TransactionModel.trans.remove(transaction);
                finish();


            }


        });*/
    }


}
