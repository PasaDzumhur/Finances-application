package com.example.rma20dzumhurpasa47;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
        Transaction transaction=getPresenter().getTransaction();
        edit5.setText(transaction.getDate().toString());
        edit2.setText(""+transaction.getAmount());
        edit1.setText(transaction.getTitle());
        edit3.setText(transaction.getType().toString());
        edit4.setText(transaction.getItemDescription());
        edit6.setText(""+transaction.getTransactionInterval());
        if(transaction.getEndDate()==null) edit7.setText("");
        else edit7.setText(transaction.getEndDate().toString());


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
    }


}
