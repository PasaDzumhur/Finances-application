package com.example.rma20dzumhurpasa47.budgetStuff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphsFragment extends Fragment {
    private BarChart expensesChart,incomeChart,totalChart;
    private ArrayList<BarEntry> barEntryArrayList;
    private ArrayList<BarEntry> barEntryArrayList2;
    private ArrayList<BarEntry> barEntryArrayList3;
    private ArrayList<String >labelsName;
    private ArrayList<Integer> valuesExpenses;
    private ArrayList<Integer> valuesIncomes;
    private ArrayList<Integer> valuesTotal;
    //private ArrayList<Transaction> transactions=TransactionModel.trans;
    private IGraphPresenter presenter;

    public IGraphPresenter getPresenter() {
        if(presenter==null) presenter=new GraphPresenter(getActivity());
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_graphs,container,false);
        try {
            expensesChart=view.findViewById(R.id.expensesGraph);
            incomeChart=view.findViewById(R.id.incomeGraph);
            getPresenter().setTransactions(TransactionModel.trans);
            valuesExpenses=presenter.getMonthExpenses();
            valuesIncomes=presenter.getMonthIncome();
            //valuesIncomes=presenter.getMonthIncome();
            valuesTotal=new ArrayList<>();
            barEntryArrayList=new ArrayList<>();
            labelsName=new ArrayList<>();
            /*for(int i=0; i<12; i++){
                valuesTotal.add(valuesIncomes.get(i)-valuesExpenses.get(i));
            }*/
            for(int i=1; i<=12; i++){
                barEntryArrayList.add(new BarEntry(i,valuesExpenses.get(i-1)));
                String pom=""+i;
                labelsName.add(pom);
            }
            BarDataSet barDataSet=new BarDataSet(barEntryArrayList,"MonthlyExpenses");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            Description description=new Description();
            description.setText("Months");
            expensesChart.setDescription(description);
            BarData barData=new BarData(barDataSet);
            expensesChart.setData(barData);
            XAxis xAxis = expensesChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setLabelCount(labelsName.size());
            xAxis.setLabelRotationAngle(270);
            expensesChart.animateY(2000);
            expensesChart.invalidate();

            //barEntryArrayList.clear();
            barEntryArrayList2=new ArrayList<>();
            for(int i=1; i<=12; i++){
                barEntryArrayList2.add(new BarEntry(i,valuesIncomes.get(i-1)));
                //String pom=""+i;
                //labelsName.add(pom);
            }
            BarDataSet barDataSet2=new BarDataSet(barEntryArrayList,"MonthlyIncomes");
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
            //description=new Description();
            //description.setText("Months");
            incomeChart.setDescription(description);
            BarData barData2 = new BarData(barDataSet2);
            incomeChart.setData(barData2);
            XAxis xAxis2 = incomeChart.getXAxis();
            xAxis2.setValueFormatter(new IndexAxisValueFormatter(labelsName));
            xAxis2.setPosition(XAxis.XAxisPosition.TOP);
            xAxis2.setDrawGridLines(false);
            xAxis2.setDrawAxisLine(false);
            xAxis2.setLabelCount(labelsName.size());
            xAxis2.setLabelRotationAngle(270);
            expensesChart.animateY(2000);
            expensesChart.invalidate();



        }catch (Exception e){
            e.printStackTrace();
        }






        return view;
    }
}
