package com.example.rma20dzumhurpasa47.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Account;
import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import static com.example.rma20dzumhurpasa47.list.MainActivity.calendar;

public class TransactionListFragment extends Fragment implements ITransactionListView {

    private ListView list;
    private ITransactionListPresenter transListPresenter;
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
    public static Account account=new Account(100000,100000,100000);

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

    public interface OnItemClick {
        public void onItemClicked(Transaction transaction);
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


        View fragmentView = inflater.inflate(R.layout.fragment_list,container,false);
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
        double stanje=account.getBudget()-account.workTheTransactions(TransactionModel.trans);
        text1.setText("Global amount: "+stanje);
        text2.setText("Limit: "+account.getTotalLimit());
        list.setOnItemClickListener(listItemClickListener);
        btnAddTrans=fragmentView.findViewById(R.id.btnAddTrans);
        calendar.setTime(new Date(System.currentTimeMillis()));
        textMonth=fragmentView.findViewById(R.id.textMonth);
        textMonth.setText(new SimpleDateFormat("MMMM").format(calendar.getTime()));
        right=fragmentView.findViewById(R.id.right);
        left=fragmentView.findViewById(R.id.left);
        getPresenter().refreshTransactions();
        onItemClick=(OnItemClick)getActivity();
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
        return fragmentView;





    }


    @Override
    public void setTransactions(ArrayList<Transaction> trans) {

    }

    @Override
    public void notifyTransactionListDataSetChanged() {

    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Transaction transaction = adapter1.getItem(position);
            onItemClick.onItemClicked(transaction);
        }
    };
}
