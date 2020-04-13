package com.example.rma20dzumhurpasa47.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;

import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {

    int resource;

    private TextView title;
    private TextView amount;
    private ImageView icon;



    public TransactionListAdapter(@NonNull Context context, int _resource, ArrayList<Transaction> items) {
        super(context, _resource, items);
        resource=_resource;
    }
    public ArrayList<Transaction> getItems(){
        return this.getItems();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout newView;
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout)convertView;
        }

        Transaction trans = getItem(position);
        title=newView.findViewById(R.id.title);
        amount=newView.findViewById(R.id.amount);
        icon=newView.findViewById(R.id.icon);
        String pom=trans.getType().toString();
        Integer slika= TransactionModel.slikice.get(pom);
        if(slika==null) slika=R.drawable.unnamed;
        icon.setImageResource(slika);
        title.setText(trans.getTitle());
        String s="";
        s+=trans.getAmount();
        amount.setText(s);



        return newView;
    }

    public void setTransactions(ArrayList<Transaction> trans) {
        this.clear();
        this.addAll(trans);
    }
}
