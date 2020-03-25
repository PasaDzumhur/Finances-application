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
    private TransactionListAdapter adapter1;
    private MySpinnerAdapter adapter2;

    private EditText text1;
    private EditText text2;
    private Spinner spinner1;
    private ImageButton left;
    private EditText textMonth;
    private ImageButton right;
    private Spinner spinner2;
    private Button btnAddTrans;
    private ArrayList<String> types=new ArrayList<String>(){{
        add("REGULARINCOME");
        add("PURCHASE");
        add("INDIVIDUALINCOME");
        add("REGULARPAYMENT");
        add("INDIVIDUALPAYMENT");
    }};


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
        adapter1=new TransactionListAdapter(getApplicationContext(),R.layout.list_element,new ArrayList<Transaction>());
        adapter2=new MySpinnerAdapter(getApplicationContext(),R.layout.spinner_element,types);
        list=(ListView) findViewById(R.id.list);
        list.setAdapter(adapter1);
        getPresenter().refreshTransactions();
        spinner1=(Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter2);

    }

    @Override
    public void setTransactions(ArrayList<Transaction> trans) {
        adapter1.setTransactions(trans);

    }

    @Override
    public void notifyTransactionListDataSetChanged() {
        adapter1.notifyDataSetChanged();

    }
}
