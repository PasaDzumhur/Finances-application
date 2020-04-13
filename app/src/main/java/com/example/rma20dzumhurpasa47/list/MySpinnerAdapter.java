package com.example.rma20dzumhurpasa47.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.TransactionModel;

import java.util.ArrayList;

public class MySpinnerAdapter extends ArrayAdapter {
    private int resource;


    private ImageView imageView;
    private TextView typeText;

    public MySpinnerAdapter(@NonNull Context context, int _resource, ArrayList<String> items) {
        super(context, _resource,items);
        resource=_resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

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
            image.setImageResource(TransactionModel.slikice.get(currentItem));
            textView.setText(currentItem);
        }

        return convertView;
    }

}
