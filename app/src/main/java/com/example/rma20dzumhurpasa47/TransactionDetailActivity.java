package com.example.rma20dzumhurpasa47;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDetailActivity extends AppCompatActivity {
    private EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7;
    private Button btnSave,btnDelete;

    private ITransactionDetailPresenter presenter;

    public ITransactionDetailPresenter getPresenter(){
        if(presenter==null){
            presenter=new TransactionDetailPresenter(this);
        }
        return presenter;
    }

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_detail);
        /*getPresenter().create(getIntent().getSerializableExtra("date"),getIntent().getStringExtra("amount"),
                getIntent().getStringExtra("title"),getIntent().getStringExtra("type"),
                getIntent().getStringExtra("itemDescription"),getIntent().getStringExtra("transactionInterval"),
                getIntent().getStringExtra("endDate"));*/

        Bundle extras = getIntent().getExtras();
        getPresenter().create((Date)extras.get("date"),(double)extras.get("amount"),(String)extras.get("title"),
                (Transaction.Type)extras.get("type"),(String)extras.get("itemDescription"),(int)extras.get("transactionInterval"),
                (Date)extras.get("endDate"));
        edit1=findViewById(R.id.edit1);
        edit2=findViewById(R.id.edit2);
        edit3=findViewById(R.id.edit3);
        edit4=findViewById(R.id.edit4);
        edit5=findViewById(R.id.edit5);
        edit6=findViewById(R.id.edit6);
        edit7=findViewById(R.id.edit7);
        btnDelete=findViewById(R.id.btnDelete);
        btnSave=findViewById(R.id.btnSave);
        final Transaction transaction=getPresenter().getTransaction();


        final SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");




        edit5.setText(formatter.format(transaction.getDate()));
        edit2.setText(""+transaction.getAmount());
        edit1.setText(transaction.getTitle());
        edit3.setText(transaction.getType().toString());
        edit4.setText(transaction.getItemDescription());
        edit6.setText(""+transaction.getTransactionInterval());
        if(transaction.getEndDate()==null) edit7.setText("");
        else edit7.setText(formatter.format(transaction.getEndDate()));


        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit1.setBackgroundColor(Color.GREEN);

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
                    Transaction.Type pom= Transaction.Type.gimmeType(edit3.getText().toString());

                    Transaction newTransaction = new Transaction(formatter.parse(edit5.getText().toString()),Double.parseDouble(edit2.getText().toString()),edit1.getText().toString(),
                            pom,edit4.getText().toString(),Integer.parseInt(edit6.getText().toString()),formatter.parse(edit7.getText().toString()));
                    if(newTransaction.equals(transaction)) finish();
                    else{




                        TransactionModel.trans.remove(transaction);
                        TransactionModel.trans.add(newTransaction);
                        finish();

                    }
                } catch (ParseException e) {

                } catch (IllegalArgumentException r){

                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    AlertDialog alert = new AlertDialog.Builder(TransactionDetailActivity.this).setTitle("Warning!!!!!!!").setMessage("Are you sure you want to delete it???")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Transaction pom = new Transaction(transaction.getDate(), transaction.getAmount(), transaction.getTitle(), transaction.getType(), transaction.getItemDescription(), transaction.getTransactionInterval(), transaction.getEndDate());

                            TransactionModel.trans.remove(transaction);
                            finish();
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();


                }
                catch (Exception e){

                }

            }


        });
    }


}
