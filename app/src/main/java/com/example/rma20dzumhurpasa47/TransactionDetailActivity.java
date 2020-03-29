package com.example.rma20dzumhurpasa47;

import android.os.Bundle;
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
        edit1.setText(transaction.getDate().toString());
        edit2.setText(""+transaction.getAmount());
        edit3.setText(transaction.getTitle());
        edit4.setText(transaction.getType().toString());
        edit5.setText(transaction.getItemDescription());
        edit6.setText(""+transaction.getTransactionInterval());
        if(transaction.getEndDate()==null) edit7.setText("");
        else edit7.setText(transaction.getEndDate().toString());
    }


}
