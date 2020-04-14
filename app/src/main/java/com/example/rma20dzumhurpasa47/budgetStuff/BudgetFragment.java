package com.example.rma20dzumhurpasa47.budgetStuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Account;
import com.example.rma20dzumhurpasa47.list.MainActivity;

import static com.example.rma20dzumhurpasa47.list.MainActivity.account;

public class BudgetFragment extends Fragment {
    //private TextView budgetView,totalView,monthlyView;
    private EditText budgetText,totalText,monthlyText;
    private Button btnSaveAcc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        budgetText=view.findViewById(R.id.budgetText);
        //budgetView=view.findViewById(R.id.budgetView);
        totalText=view.findViewById(R.id.totalText);
        //totalView=view.findViewById(R.id.totalView);
        monthlyText=view.findViewById(R.id.monthlyText);
        //monthlyView=view.findViewById(R.id.monthlyView);
        btnSaveAcc=view.findViewById(R.id.btnSaveAcc);
        budgetText.setText(""+ account.getBudget());
        monthlyText.setText(""+ account.getMonthLimit());
        totalText.setText(""+ account.getTotalLimit());


        btnSaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double newBudget = Double.parseDouble(budgetText.getText().toString());
                    Double newTotal = Double.parseDouble(totalText.getText().toString());
                    Double newMonthly = Double.parseDouble(monthlyText.getText().toString());
                    Account test=new Account(newBudget,newTotal,newMonthly);
                    account=test;
                }catch (Exception e){

                }
            }
        });
        return view;



    }
}
