package com.example.rma20dzumhurpasa47;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ITransactionListView {
    private ListView list;
    private ITransactionListPresenter transListPresenter;
    private TransactionListAdapter adapter;

    private EditText text1;
    private EditText text2;
    private Spinner spinner1;
    private ImageButton left;
    private EditText textMonth;
    private ImageButton right;
    private Spinner spinner2;
    private Button btnAddTrans;


    public ITransactionListPresenter getPresenter(){
        if(transListPresenter==null){
            transListPresenter=new TransactionListPresenter(this,this);
        }
        return transListPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter=new TransactionListAdapter(getApplicationContext(),R.layout.list_element,new ArrayList<Transaction>());
        list=(ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        getPresenter().refreshTransactions();
    }

    @Override
    public void setTransactions(ArrayList<Transaction> trans) {
        adapter.setTransactions(trans);

    }

    @Override
    public void notifyTransactionListDataSetChanged() {
        adapter.notifyDataSetChanged();

    }
}
