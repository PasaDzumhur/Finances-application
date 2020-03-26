package com.example.rma20dzumhurpasa47;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionListPresenter implements ITransactionListPresenter {

    private ITransactionListView view;
    private ITransactionListInteractor interactor;
    private Context context;

    public TransactionListPresenter(ITransactionListView view, Context context) {
        this.view = view;
        this.interactor=new TransactionListInteractor();
        this.context = context;
    }

    @Override
    public void refreshTransactions() {
        view.setTransactions(interactor.get());
        view.notifyTransactionListDataSetChanged();

    }


    private void sortByTitle(ArrayList<Transaction> list){
        for(int i=0; i<list.size()-1; i++){
            for(int j=i+1; j<list.size(); j++){
                if(list.get(i).getTitle().compareTo(list.get(j).getTitle())>0) {
                    Transaction pom=list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,pom);
                }
            }
        }
    }



    private void sortByPrice(ArrayList<Transaction> list){
        for(int i=0; i<list.size()-1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if(list.get(i).getAmount()<list.get(j).getAmount()){
                    Transaction pom=list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,pom);
                }
            }
        }
    }

    private void sortByDate(ArrayList<Transaction> list){
        for(int i=0; i<list.size()-1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if(list.get(i).getDate().before(list.get(j).getDate())){
                    Transaction pom=list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,pom);
                }
            }
        }
    }

    @Override
    public void refreshSortTitleAsc() {
        ArrayList<Transaction> list=new ArrayList<>();
        list.addAll(interactor.get());
        sortByTitle(list);
        view.setTransactions(list);
        view.notifyTransactionListDataSetChanged();


    }

    @Override
    public void refreshSortTitleDesc() {
        ArrayList<Transaction> list=new ArrayList<>();
        list.addAll(interactor.get());
        sortByTitle(list);
        ArrayList<Transaction> reverseList=new ArrayList<>();
        for(Transaction t : list){
            reverseList.add(0,t);
        }
        view.setTransactions(reverseList);
        view.notifyTransactionListDataSetChanged();

    }

    @Override
    public void refreshSortPriceDesc() {
        ArrayList<Transaction> list=new ArrayList<>();
        list.addAll(interactor.get());
        sortByPrice(list);
        view.setTransactions(list);
        view.notifyTransactionListDataSetChanged();
    }

    @Override
    public void refreshSortPriceAsc() {
        ArrayList<Transaction> list=new ArrayList<>();
        list.addAll(interactor.get());
        sortByPrice(list);
        ArrayList<Transaction> reverseList=new ArrayList<>();
        for(Transaction t : list){
            reverseList.add(0,t);
        }
        view.setTransactions(reverseList);
        view.notifyTransactionListDataSetChanged();

    }

    @Override
    public void refreshSortDateDesc() {
        ArrayList<Transaction> list=new ArrayList<>();
        list.addAll(interactor.get());
        sortByDate(list);
        ArrayList<Transaction> reverseList=new ArrayList<>();
        for(Transaction t : list){
            reverseList.add(0,t);
        }
        view.setTransactions(reverseList);
        view.notifyTransactionListDataSetChanged();

    }

    @Override
    public void refreshSortDateAsc() {
        ArrayList<Transaction> list=new ArrayList<>();
        list.addAll(interactor.get());
        sortByDate(list);
        view.setTransactions(list);
        view.notifyTransactionListDataSetChanged();
    }
}
