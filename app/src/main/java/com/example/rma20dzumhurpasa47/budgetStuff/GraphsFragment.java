package com.example.rma20dzumhurpasa47.budgetStuff;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    private Spinner spinner;
    private ArrayAdapter<String > adapter;
    //private ArrayList<Transaction> transactions=TransactionModel.trans;
    private IGraphPresenter presenter;

    public IGraphPresenter getPresenter() {
        if(presenter==null) presenter=new GraphPresenter(getActivity());
        return presenter;
    }


    //problemi oko labela grafa pa zato pokusavam sa ovim glupim nacinima
    private ArrayList<String> dajMjesece(){
        ArrayList<String> labels=new ArrayList<String>(){
            {
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                add("6");
                add("7");
                add("8");
                add("9");
                add("10");
                add("11");
                add("12");
            }
        };
        return labels;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_graphs,container,false);
        try {
            expensesChart=view.findViewById(R.id.expensesGraph);
            incomeChart=view.findViewById(R.id.incomeGraph);
            totalChart=view.findViewById(R.id.totalGraph);
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
            labelsName.addAll(dajMjesece());
            for(int i=0; i<12; i++){
                barEntryArrayList.add(new BarEntry(i,valuesExpenses.get(i)));
                //String pom=""+i;
                //labelsName.add(pom);
            }
            BarDataSet barDataSet=new BarDataSet(barEntryArrayList,"MonthlyExpenses");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            final Description description=new Description();
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
            for(int i=0; i<12; i++){
                barEntryArrayList2.add(new BarEntry(i,valuesIncomes.get(i)));
                //String pom=""+i;
                //labelsName.add(pom);
            }
            BarDataSet barDataSet2=new BarDataSet(barEntryArrayList2,"MonthlyIncomes");
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
            incomeChart.animateY(2000);
            incomeChart.invalidate();

            barEntryArrayList3=new ArrayList<>();
            for(int i=0; i<12; i++){
                barEntryArrayList3.add(new BarEntry(i,valuesIncomes.get(i)-valuesExpenses.get(i)));
            }
            BarDataSet barDataSet3=new BarDataSet(barEntryArrayList3,"MonthlyTotalStatus");
            barDataSet3.setColors(Color.RED);
            totalChart.setDescription(description);
            BarData barData3=new BarData(barDataSet3);
            totalChart.setData(barData3);
            XAxis xAxis3 = incomeChart.getXAxis();
            xAxis3.setValueFormatter(new IndexAxisValueFormatter(labelsName));
            xAxis3.setPosition(XAxis.XAxisPosition.TOP);
            xAxis3.setDrawGridLines(false);
            xAxis3.setDrawAxisLine(false);
            xAxis3.setLabelCount(labelsName.size());
            xAxis3.setLabelRotationAngle(270);
            totalChart.animateY(2000);
            totalChart.invalidate();
            ArrayList<String> graphFilter=new ArrayList<>();
            graphFilter.add("Monthly");
            graphFilter.add("Weekly");
            graphFilter.add("Daily");
            adapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,graphFilter);
            spinner=view.findViewById(R.id.spinner);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String pomocni = (String) spinner.getSelectedItem();
                        System.out.print(pomocni);
                        Description description1=new Description();
                        if (pomocni.equals("Weekly")) {
                            valuesExpenses = getPresenter().getWeeklyExpenses();
                            valuesIncomes = getPresenter().getWeeklyExpenses();
                            valuesTotal = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                valuesTotal.add(valuesIncomes.get(i) - valuesExpenses.get(i));
                            }
                            labelsName = new ArrayList<>();
                            labelsName.add("1");
                            labelsName.add("2");
                            labelsName.add("3");
                            labelsName.add("4");
                            labelsName.add("5");
                            description.setText("Weeks");

                        } else if (pomocni.equals("Daily")) {
                            valuesExpenses = getPresenter().getDailyExpenses();
                            valuesIncomes = getPresenter().getDailyIncome();
                            for (int i = 0; i < valuesIncomes.size(); i++) {
                                valuesTotal.add(valuesIncomes.get(i) - valuesExpenses.get(i));
                            }
                            labelsName = new ArrayList<>();
                            for (int i = 1; i <= 31; i++) {
                                labelsName.add("" + i);
                            }
                            description.setText("Days");

                        } else if(pomocni.equals("Monthly")) {
                            valuesExpenses = presenter.getMonthExpenses();
                            valuesIncomes = presenter.getMonthIncome();
                            valuesTotal=new ArrayList<>();
                            for(int i=0; i<12; i++){
                                valuesTotal.add(valuesIncomes.get(i) - valuesExpenses.get(i));
                            }
                            labelsName=dajMjesece();
                            description.setText("Months");
                        }


                        //-----------------------------------------------------------------


                        barEntryArrayList=new ArrayList<>();
                        barEntryArrayList2=new ArrayList<>();
                        barEntryArrayList3=new ArrayList<>();
                        for(int i=0; i<valuesExpenses.size(); i++){
                            barEntryArrayList.add(new BarEntry(i,valuesExpenses.get(i)));
                        }
                        for(int i=0; i<valuesIncomes.size(); i++){
                            barEntryArrayList2.add(new BarEntry(i,valuesIncomes.get(i)));
                        }
                        for(int i=0; i<valuesTotal.size(); i++){
                            barEntryArrayList3.add(new BarEntry(i,valuesTotal.get(i)));
                        }

                        BarDataSet expenses=new BarDataSet(barEntryArrayList,"Expenses");
                        BarDataSet incomes=new BarDataSet(barEntryArrayList2,"Income");
                        BarDataSet total=new BarDataSet(barEntryArrayList3,"Total");
                        expenses.setColors(Color.RED);
                        incomes.setColors(Color.GREEN);
                        total.setColors(Color.BLUE);
                        expensesChart.setDescription(description);
                        incomeChart.setDescription(description);
                        totalChart.setDescription(description);
                        BarData incomeData=new BarData(incomes);
                        BarData expensesData=new BarData(expenses);
                        BarData totalData=new BarData(total);
                        expensesChart.setData(expensesData);
                        incomeChart.setData(incomeData);
                        totalChart.setData(totalData);

                        XAxis xAxis = expensesChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));
                        xAxis.setPosition(XAxis.XAxisPosition.TOP);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setLabelCount(labelsName.size());
                        xAxis.setLabelRotationAngle(270);
                        expensesChart.animateY(2000);
                        expensesChart.invalidate();

                        XAxis xAxis2 = incomeChart.getXAxis();
                        xAxis2.setValueFormatter(new IndexAxisValueFormatter(labelsName));
                        xAxis2.setPosition(XAxis.XAxisPosition.TOP);
                        xAxis2.setDrawGridLines(false);
                        xAxis2.setDrawAxisLine(false);
                        xAxis2.setLabelCount(labelsName.size());
                        xAxis2.setLabelRotationAngle(270);
                        incomeChart.animateY(2000);
                        incomeChart.invalidate();


                        XAxis xAxis3 = incomeChart.getXAxis();
                        xAxis3.setValueFormatter(new IndexAxisValueFormatter(labelsName));
                        xAxis3.setPosition(XAxis.XAxisPosition.TOP);
                        xAxis3.setDrawGridLines(false);
                        xAxis3.setDrawAxisLine(false);
                        xAxis3.setLabelCount(labelsName.size());
                        xAxis3.setLabelRotationAngle(270);
                        totalChart.animateY(2000);
                        totalChart.invalidate();


                        //-----------------------------------------------------------------




                    }catch (Exception e){

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }






        return view;
    }
}
