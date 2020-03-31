package com.example.rma20dzumhurpasa47;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ITransactionListView {
    private ListView list;
    private ITransactionListPresenter transListPresenter;
    private TransactionListAdapter adapter1;
    private MySpinnerAdapter adapter2;
    private ArrayAdapter<String> adapter3;

    private Account account=new Account(100000,100000,100000);

    private TextView text1;
    private TextView text2;
    private Spinner spinner1;
    private ImageButton left;
    private TextView textMonth;
    private ImageButton right;
    private Spinner spinner2;
    private Button btnAddTrans;
    private ArrayList<String> types=new ArrayList<String>(){{
        add("GIMMEALL");
        add("PURCHASE");
        add("INDIVIDUALINCOME");
        add("REGULARPAYMENT");
        add("INDIVIDUALPAYMENT");
        add("REGULARINCOME");
    }};
    private void refreshAll(){
        if(spinner2.getSelectedItem().equals("Price - Ascending")) getPresenter().refreshSortPriceAsc();
        else if(spinner2.getSelectedItem().equals("Price - Descending")) getPresenter().refreshSortPriceDesc();
        else if(spinner2.getSelectedItem().equals("Title - Ascending")) getPresenter().refreshSortTitleAsc();
        else if(spinner2.getSelectedItem().equals("Title - Descending")) getPresenter().refreshSortTitleDesc();
        else if(spinner2.getSelectedItem().equals("Date - Ascending")) getPresenter().refreshSortDateAsc();
        else if(spinner2.getSelectedItem().equals("Date - Descending")) getPresenter().refreshSortDateDesc();
        else getPresenter().refreshTransactions();

    }
    private ArrayList<String> sorts=new ArrayList<String>(){{
        add("Price - Ascending");
        add("Price - Descending");
        add("Title - Ascending");
        add("Title - Descending");
        add("Date - Ascending");
        add("Date - Descending");
    }};
    public static Calendar calendar=Calendar.getInstance();

    private static String filter="";

    public static String getFilter() {
        return filter;
    }

    public static void setFilter(String filter) {
        MainActivity.filter = filter;
    }

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
        adapter3=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,sorts);
        list=findViewById(R.id.list);
        list.setAdapter(adapter1);
        getPresenter().refreshTransactions();
        spinner1= findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter2);
        spinner2=findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter3);
        left=findViewById(R.id.left);
        right=findViewById(R.id.right);
        textMonth=findViewById(R.id.textMonth);
        account.workTheTransactions(TransactionModel.getTrans());
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text1.setText("Global amount: "+account.getBudget());
        text2.setText("Limit: "+account.getTotalLimit());
        list.setOnItemClickListener(listItemClickListener);
        btnAddTrans=findViewById(R.id.btnAddTrans);
        calendar.setTime(new Date(System.currentTimeMillis()));
        textMonth=findViewById(R.id.textMonth);
        textMonth.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()));

        btnAddTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTransactionIntent = new Intent(MainActivity.this, AddTransactionActivity.class);
                MainActivity.this.startActivityForResult(addTransactionIntent,1);


            }
        });




        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setFilter((String) spinner1.getItemAtPosition(position));
                if(spinner2.getSelectedItem().equals("Price - Ascending")) getPresenter().refreshSortPriceAsc();
                else if(spinner2.getSelectedItem().equals("Price - Descending")) getPresenter().refreshSortPriceDesc();
                else if(spinner2.getSelectedItem().equals("Title - Ascending")) getPresenter().refreshSortTitleAsc();
                else if(spinner2.getSelectedItem().equals("Title - Descending")) getPresenter().refreshSortTitleDesc();
                else if(spinner2.getSelectedItem().equals("Date - Ascending")) getPresenter().refreshSortDateAsc();
                else if(spinner2.getSelectedItem().equals("Date - Descending")) getPresenter().refreshSortDateDesc();
                else getPresenter().refreshTransactions();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setFilter("");
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner2.getSelectedItem().equals("Price - Ascending")) getPresenter().refreshSortPriceAsc();
                else if(spinner2.getSelectedItem().equals("Price - Descending")) getPresenter().refreshSortPriceDesc();
                else if(spinner2.getSelectedItem().equals("Title - Ascending")) getPresenter().refreshSortTitleAsc();
                else if(spinner2.getSelectedItem().equals("Title - Descending")) getPresenter().refreshSortTitleDesc();
                else if(spinner2.getSelectedItem().equals("Date - Ascending")) getPresenter().refreshSortPriceAsc();
                else getPresenter().refreshTransactions();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getPresenter().refreshTransactions();

            }
        });
        spinner2.setSelection(0);
    }

    @Override
    public void setTransactions(ArrayList<Transaction> trans) {
        adapter1.setTransactions(trans);

    }

    @Override
    public void notifyTransactionListDataSetChanged() {
        adapter1.notifyDataSetChanged();

    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent transactionDetailIntent = new Intent(MainActivity.this, TransactionDetailActivity.class);
            Transaction transaction=adapter1.getItem(position);
            transactionDetailIntent.putExtra("date",transaction.getDate());
            transactionDetailIntent.putExtra("amount",transaction.getAmount());
            transactionDetailIntent.putExtra("title",transaction.getTitle());
            transactionDetailIntent.putExtra("type",transaction.getType());
            transactionDetailIntent.putExtra("itemDescription",transaction.getItemDescription());
            transactionDetailIntent.putExtra("transactionInterval",transaction.getTransactionInterval());
            transactionDetailIntent.putExtra("endDate",transaction.getEndDate());
            MainActivity.this.startActivityForResult(transactionDetailIntent,1);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1) refreshAll();

        super.onActivityResult(requestCode, resultCode, data);
    }
}
