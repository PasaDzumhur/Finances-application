package com.example.rma20dzumhurpasa47.list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Account;
import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;
import com.example.rma20dzumhurpasa47.detail.AddTransactionActivity;
import com.example.rma20dzumhurpasa47.detail.TransactionDetailActivity;
import com.example.rma20dzumhurpasa47.detail.TransactionDetailFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TransactionListFragment.OnItemClick {
    private boolean twoPaneMode;
    public static Calendar calendar=Calendar.getInstance();
    private static String filter="";

    public static String getFilter() {
        return filter;
    }
    public static void setFilter(String filter) {
        MainActivity.filter = filter;
    }

    @Override
    public void onItemClicked(Transaction transaction) {
        Bundle arguments=new Bundle();
        arguments.putParcelable("transaction",transaction);
        TransactionDetailFragment detailFragment=new TransactionDetailFragment();
        detailFragment.setArguments(arguments);
        if(twoPaneMode){
            getSupportFragmentManager().beginTransaction().replace(R.id.transactions_detail,detailFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.transactions_list,detailFragment)
                    .addToBackStack(null).commit();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar.setTime(new Date(System.currentTimeMillis()));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FrameLayout details=findViewById(R.id.transactions_detail);
        if(details!=null){
            twoPaneMode=true;
            TransactionDetailFragment detailFragment=(TransactionDetailFragment)fragmentManager.findFragmentById(R.id.transactions_detail);
            if(detailFragment==null){
                detailFragment=new TransactionDetailFragment();
                fragmentManager.beginTransaction().replace(R.id.transactions_detail,detailFragment).commit();
            }
        }else {
            twoPaneMode=false;
        }
        Fragment listFragment=fragmentManager.findFragmentById(R.id.transactions_list);
        if(listFragment==null){
            listFragment=new TransactionListFragment();
            fragmentManager.beginTransaction().replace(R.id.transactions_list,listFragment).commit();
        }
    }
    /*
    private ListView list;
    private ITransactionListPresenter transListPresenter;
    private TransactionListAdapter adapter1;
    private MySpinnerAdapter adapter2;
    private ArrayAdapter<String> adapter3;

    public static Account account=new Account(100000,100000,100000);

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
    private void refreshAll() throws ParseException {



        if(spinner2.getSelectedItem().equals("Price - Ascending")) getPresenter().refreshSortPriceAsc();
        else if(spinner2.getSelectedItem().equals("Price - Descending")) getPresenter().refreshSortPriceDesc();
        else if(spinner2.getSelectedItem().equals("Title - Ascending")) getPresenter().refreshSortTitleAsc();
        else if(spinner2.getSelectedItem().equals("Title - Descending")) getPresenter().refreshSortTitleDesc();
        else if(spinner2.getSelectedItem().equals("Date - Ascending")) getPresenter().refreshSortDateAsc();
        else if(spinner2.getSelectedItem().equals("Date - Descending")) getPresenter().refreshSortDateDesc();
        else getPresenter().refreshTransactions();
        double stanje=account.getBudget()-account.workTheTransactions(TransactionModel.trans);

        text1.setText("Global amount: "+stanje);

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
        //account.workTheTransactions(TransactionModel.getTrans());
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        double stanje=account.getBudget()-account.workTheTransactions(TransactionModel.trans);
        text1.setText("Global amount: "+stanje);
        text2.setText("Limit: "+account.getTotalLimit());
        list.setOnItemClickListener(listItemClickListener);
        btnAddTrans=findViewById(R.id.btnAddTrans);
        calendar.setTime(new Date(System.currentTimeMillis()));
        textMonth=findViewById(R.id.textMonth);
        textMonth.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()));
        right=findViewById(R.id.right);
        left=findViewById(R.id.left);

        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                textMonth.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()));
                try {
                    refreshAll();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                textMonth.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()));
                try {
                    refreshAll();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

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
                try {
                    if (spinner2.getSelectedItem().equals("Price - Ascending"))
                        getPresenter().refreshSortPriceAsc();
                    else if (spinner2.getSelectedItem().equals("Price - Descending"))
                        getPresenter().refreshSortPriceDesc();
                    else if (spinner2.getSelectedItem().equals("Title - Ascending"))
                        getPresenter().refreshSortTitleAsc();
                    else if (spinner2.getSelectedItem().equals("Title - Descending"))
                        getPresenter().refreshSortTitleDesc();
                    else if (spinner2.getSelectedItem().equals("Date - Ascending"))
                        getPresenter().refreshSortDateAsc();
                    else if (spinner2.getSelectedItem().equals("Date - Descending"))
                        getPresenter().refreshSortDateDesc();
                    else getPresenter().refreshTransactions();
                }catch (ParseException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setFilter("");
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (spinner2.getSelectedItem().equals("Price - Ascending"))
                        getPresenter().refreshSortPriceAsc();
                    else if (spinner2.getSelectedItem().equals("Price - Descending"))
                        getPresenter().refreshSortPriceDesc();
                    else if (spinner2.getSelectedItem().equals("Title - Ascending"))
                        getPresenter().refreshSortTitleAsc();
                    else if (spinner2.getSelectedItem().equals("Title - Descending"))
                        getPresenter().refreshSortTitleDesc();
                    else if (spinner2.getSelectedItem().equals("Date - Ascending"))
                        getPresenter().refreshSortPriceAsc();
                    else getPresenter().refreshTransactions();
                }catch (ParseException e){
                    e.printStackTrace();
                }
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
        if(requestCode==1) {
            try {

                refreshAll();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
