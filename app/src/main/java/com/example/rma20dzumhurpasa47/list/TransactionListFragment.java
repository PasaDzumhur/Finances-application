package com.example.rma20dzumhurpasa47.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Account;
import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;
import com.example.rma20dzumhurpasa47.detail.TransactionDetailFragment;
import com.example.rma20dzumhurpasa47.util.OnSwipeTouchListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import static com.example.rma20dzumhurpasa47.list.MainActivity.calendar;
import static com.example.rma20dzumhurpasa47.list.MainActivity.setFilter;

public class TransactionListFragment extends Fragment implements ITransactionListView{

    private ListView list;
    private ITransactionListPresenter transListPresenter=null;
    private TransactionListAdapter adapter1;
    private MySpinnerAdapter adapter2;
    private ArrayAdapter<String> adapter3;
    private TextView text1;
    private TextView text2;
    private Spinner spinner1;
    private ImageButton left;
    private TextView textMonth;
    private ImageButton right;
    private Spinner spinner2;
    private Button btnAddTrans;
    //private Button button;
    //public static Account account=new Account(100000,100000,100000);

    private ArrayList<String> types=new ArrayList<String>(){{
        add("GIMMEALL");
        add("PURCHASE");
        add("INDIVIDUALINCOME");
        add("REGULARPAYMENT");
        add("INDIVIDUALPAYMENT");
        add("REGULARINCOME");
    }};

    private ArrayList<String> sorts=new ArrayList<String>(){{
        add("Price - Ascending");
        add("Price - Descending");
        add("Title - Ascending");
        add("Title - Descending");
        add("Date - Ascending");
        add("Date - Descending");
    }};

    public ITransactionListPresenter getPresenter(){
        if(transListPresenter==null) transListPresenter=new TransactionListPresenter(this,getActivity());
        return transListPresenter;
    }
    private OnItemClick onItemClick;
    private OnItemClick addTransClick;
    //private OnItemClick buttonClick;
    private OnItemClick swipe;




    public interface OnItemClick {
        public void onItemClicked(Transaction transaction);
        public void addTransClicked();
        //public void buttonClicked();
        public void swiped();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState){

        getPresenter();
        final View fragmentView = inflater.inflate(R.layout.fragment_list,container,false);
        adapter1=new TransactionListAdapter(getActivity(),R.layout.list_element,new ArrayList<Transaction>());
        adapter2=new MySpinnerAdapter(getActivity(),R.layout.spinner_element,types);
        adapter3=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,sorts);
        list=fragmentView.findViewById(R.id.list);
        list.setAdapter(adapter1);
        getPresenter().refreshTransactions();
        spinner1= fragmentView.findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter2);
        spinner2=fragmentView.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter3);
        left=fragmentView.findViewById(R.id.left);
        right=fragmentView.findViewById(R.id.right);

        textMonth=fragmentView.findViewById(R.id.textMonth);
        //account.workTheTransactions(TransactionModel.getTrans());
        text1=fragmentView.findViewById(R.id.text1);
        text2=fragmentView.findViewById(R.id.text2);
        double stanje=MainActivity.account.getBudget()-MainActivity.account.workTheTransactions(TransactionModel.trans);
        text1.setText("Global amount: "+stanje);
        text2.setText("Limit: "+MainActivity.account.getTotalLimit());
        list.setOnItemClickListener(listItemClickListener);
        btnAddTrans=fragmentView.findViewById(R.id.btnAddTrans);
        //button=fragmentView.findViewById(R.id.button);
        //calendar.setTime(new Date(System.currentTimeMillis()));
        textMonth=fragmentView.findViewById(R.id.textMonth);
        textMonth.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()));
        right=fragmentView.findViewById(R.id.right);
        left=fragmentView.findViewById(R.id.left);
        getPresenter().refreshTransactions();
        onItemClick=(OnItemClick)getActivity();
        addTransClick=(OnItemClick)getActivity();
        //buttonClick=(OnItemClick)getActivity();
        swipe=(OnItemClick)getActivity();
        Intent intent = getActivity().getIntent();
        String action=intent.getAction();
        String type = intent.getType();
        /*
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    editText.setText(sharedText);
                }
            }
        }*/
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick.buttonClicked();
            }
        });*/

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setFilter((String) spinner1.getItemAtPosition(position));
                /*
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
                }*/
                String typeString = (String) spinner1.getItemAtPosition(position);
                //if(!typeString.equals("GIMMEALL")) {
                    Transaction.Type type = Transaction.Type.gimmeType((String) spinner1.getItemAtPosition(position));
                    //setTransactions(getPresenter().filter(type));
                    setTransactions(getPresenter().getTransactions());
                    notifyTransactionListDataSetChanged();
                //}else{
                 //   setTransactions(getPresenter().getTransactions());
                 //   notifyTransactionListDataSetChanged();
                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setFilter("");
            }
        });

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

        btnAddTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransClick.addTransClicked();
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



        fragmentView.setOnTouchListener(new OnSwipeTouchListener(getActivity()){
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                //buttonClick.buttonClicked();
                swipe.swiped();
            }
        });


        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ScrollView mScrollView = fragmentView.findViewById(R.id.scrollViewList);
                mScrollView.requestDisallowInterceptTouchEvent(true);
                int action = motionEvent.getActionMasked();
                switch (action){
                    case MotionEvent.ACTION_UP:
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        //getPresenter().refreshTransactions();
        //setTransactions(getPresenter().getTransactions());
        //adapter1.notifyDataSetChanged();
        return fragmentView;


    }
    private void refreshAll() throws ParseException {



        if(spinner2.getSelectedItem().equals("Price - Ascending")) getPresenter().refreshSortPriceAsc();
        else if(spinner2.getSelectedItem().equals("Price - Descending")) getPresenter().refreshSortPriceDesc();
        else if(spinner2.getSelectedItem().equals("Title - Ascending")) getPresenter().refreshSortTitleAsc();
        else if(spinner2.getSelectedItem().equals("Title - Descending")) getPresenter().refreshSortTitleDesc();
        else if(spinner2.getSelectedItem().equals("Date - Ascending")) getPresenter().refreshSortDateAsc();
        else if(spinner2.getSelectedItem().equals("Date - Descending")) getPresenter().refreshSortDateDesc();
        else getPresenter().refreshTransactions();
        double stanje=MainActivity.account.getBudget()-MainActivity.account.workTheTransactions(TransactionModel.trans);

        text1.setText("Global amount: "+stanje);

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
            //list.getChildAt(position).setBackgroundColor(Color.GREEN);
            Transaction transaction = adapter1.getItem(position);
            ArrayList<Transaction> all = getPresenter().getTransactions();
            for(Transaction trans : all){
                if(transaction.getId()==trans.getId() && trans.getDate().before(transaction.getDate())) transaction = trans;
            }
            onItemClick.onItemClicked(transaction);
        }
    };
}
