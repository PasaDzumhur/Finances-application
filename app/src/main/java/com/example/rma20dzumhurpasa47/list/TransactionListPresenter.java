package com.example.rma20dzumhurpasa47.list;

import android.content.Context;

import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class TransactionListPresenter implements ITransactionListPresenter, TransactionListInteractor.TransactionSearchDone {

    private ITransactionListView view;
    private ITransactionListInteractor interactor;
    private Context context;

    public TransactionListPresenter(ITransactionListView view, Context context) {
        this.view = view;
        //new TransactionListInteractor((TransactionListInteractor.OnMoviesSearchDone)this).execute("");
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"amount.asc");
        this.context = context;
    }

    @Override
    public void refreshTransactions() {
        //view.setTransactions(interactor.getTransactions());
        //view.notifyTransactionListDataSetChanged();

    }
    private static ArrayList<Transaction> filter(ArrayList<Transaction> list){
        Calendar calendarHelp = Calendar.getInstance();
        ArrayList<Transaction> filter = new ArrayList<>();
        Transaction.Type type = Transaction.Type.gimmeType(MainActivity.getFilter());
        for(Transaction transaction : list){
            calendarHelp.setTime(transaction.getDate());
            if(calendarHelp.get(Calendar.MONTH)==MainActivity.calendar.get(Calendar.MONTH)
            && calendarHelp.get(Calendar.YEAR)==MainActivity.calendar.get(Calendar.YEAR)) {
                if(type == null) filter.add(transaction);
                else if(transaction.getType()==type) filter.add(transaction);
            }
        }
        return filter;
    }


    private void sortByTitle(ArrayList<Transaction> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getTitle().compareTo(list.get(j).getTitle()) > 0) {
                    Transaction pom = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, pom);
                }
            }
        }
    }


    private void sortByPrice(ArrayList<Transaction> list) throws ParseException {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (Transaction.obradiRegular(list.get(i), MainActivity.calendar) < Transaction.obradiRegular(list.get(j), MainActivity.calendar)) {
                    Transaction pom = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, pom);
                }
            }
        }
    }

    private void sortByDate(ArrayList<Transaction> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getDate().before(list.get(j).getDate())) {
                    Transaction pom = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, pom);
                }
            }
        }
    }

    @Override
    public void refreshSortTitleAsc() {
        /*
        ArrayList<Transaction> list = new ArrayList<>();
        list.addAll(interactor.getTransactions());
        sortByTitle(list);
        view.setTransactions(list);
        view.notifyTransactionListDataSetChanged();*/
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"title.asc");


    }

    @Override
    public void refreshSortTitleDesc() {
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"title.desc");
        /*
        ArrayList<Transaction> list = new ArrayList<>();
        list.addAll(interactor.getTransactions());
        sortByTitle(list);
        ArrayList<Transaction> reverseList = new ArrayList<>();
        for (Transaction t : list) {
            reverseList.add(0, t);
        }
        view.setTransactions(reverseList);
        view.notifyTransactionListDataSetChanged();*/


    }

    @Override
    public void refreshSortPriceDesc() throws ParseException {
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"amount.desc");
        /*
        ArrayList<Transaction> list = new ArrayList<>();
        list.addAll(interactor.getTransactions());
        sortByPrice(list);
        view.setTransactions(list);
        view.notifyTransactionListDataSetChanged();*/
    }

    @Override
    public void refreshSortPriceAsc() throws ParseException {
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"amount.asc");
        /*
        ArrayList<Transaction> list = new ArrayList<>();
        list.addAll(interactor.getTransactions());
        sortByPrice(list);
        ArrayList<Transaction> reverseList = new ArrayList<>();
        for (Transaction t : list) {
            reverseList.add(0, t);
        }
        view.setTransactions(reverseList);
        view.notifyTransactionListDataSetChanged();*/

    }

    @Override
    public void refreshSortDateDesc() {
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"date.desc");
        /*
        ArrayList<Transaction> list = new ArrayList<>();
        list.addAll(interactor.getTransactions());
        sortByDate(list);
        ArrayList<Transaction> reverseList = new ArrayList<>();
        for (Transaction t : list) {
            reverseList.add(0, t);
        }
        view.setTransactions(reverseList);
        view.notifyTransactionListDataSetChanged();*/

    }

    @Override
    public ArrayList<Transaction> getTransactions() {
        return filter(interactor.getTransactions());
    }

    @Override
    public ArrayList<Transaction> getTypeFilter(Transaction.Type type) {

        ArrayList<Transaction> filter = new ArrayList<>();
        ArrayList<Transaction> unfiltered = interactor.getTransactions();
        for(Transaction transaction : unfiltered){
            if(transaction.getType()==type) filter.add(transaction);
        }
        return filter;
    }

    @Override
    public void refreshSortDateAsc() {
        /*
        ArrayList<Transaction> list = new ArrayList<>();
        list.addAll(interactor.getTransactions());
        sortByDate(list);
        view.setTransactions(list);
        view.notifyTransactionListDataSetChanged();*/
        this.interactor = new TransactionListInteractor((TransactionListInteractor.TransactionSearchDone)this,"date.asc");
    }

    @Override
    public void onDone(ArrayList<Transaction> results) {
        view.setTransactions(filter(getTransactions()));
        TransactionModel.trans.addAll(getTransactions());
        view.notifyTransactionListDataSetChanged();
    }
}
