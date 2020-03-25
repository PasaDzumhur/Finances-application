package com.example.rma20dzumhurpasa47;

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

import java.util.ArrayList;

public class MySpinnerAdapter extends ArrayAdapter {
    private int resource;
    /*
    private ArrayList<Transaction.Type> types=new ArrayList<Transaction.Type>(){{
        types.add(Transaction.Type.REGULARINCOME);
        types.add(Transaction.Type.INDIVIDUALINCOME);
        types.add(Transaction.Type.PURCHASE);
        types.add(Transaction.Type.INDIVIDUALPAYMENT);
        types.add(Transaction.Type.REGULARPAYMENT);
    }};*/


    private ImageView imageView;
    private TextView typeText;

    public MySpinnerAdapter(@NonNull Context context, int _resource, ArrayList<String> items) {
        super(context, _resource,items);
        resource=_resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
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

        String tip =(String)getItem(position);
        imageView=newView.findViewById(R.id.imageView);
        typeText=newView.findViewById(R.id.typeText);

        typeText.setText(tip);
        imageView.setImageResource(R.drawable.download);





        return newView;*/
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_element, parent, false
            );
        }

        ImageView image = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.typeText);

        String currentItem = (String) getItem(position);

        if (currentItem != null) {
            image.setImageResource(R.drawable.download);
            textView.setText(currentItem);
        }

        return convertView;
    }

}
