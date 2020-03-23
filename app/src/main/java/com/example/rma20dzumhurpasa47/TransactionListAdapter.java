package com.example.rma20dzumhurpasa47;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<Transaction> {
    int resource;
    public TransactionListAdapter(@NonNull Context context, int _resource, ArrayList<Transaction> items) {
        super(context, _resource, items);
        resource=_resource;
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
        /*
        titleView = newView.findViewById(R.id.title);
        genreView = newView.findViewById(R.id.genre);
        imageView = newView.findViewById(R.id.icon);
        titleView.setText(movie.getTitle());
        genreView.setText(movie.getGenre());

        String genreMatch = movie.getGenre();
        try {
            Class res = R.drawable.class;
            Field field = res.getField(genreMatch);
            int drawableId = field.getInt(null);
            imageView.setImageResource(drawableId);
        }
        catch (Exception e) {
            imageView.setImageResource(R.drawable.picture1);
        }*/

        return newView;
    }
}
