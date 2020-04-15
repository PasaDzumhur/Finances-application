package com.example.rma20dzumhurpasa47.detail;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.rma20dzumhurpasa47.R;
import com.example.rma20dzumhurpasa47.data.Transaction;
import com.example.rma20dzumhurpasa47.data.TransactionModel;
import com.example.rma20dzumhurpasa47.list.MainActivity;
import com.example.rma20dzumhurpasa47.list.TransactionListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.rma20dzumhurpasa47.list.MainActivity.account;

public class TransactionDetailFragment extends Fragment {
    private EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7;
    private Button btnSave, btnDelete;
    private Transaction selectedTransaction=null;




    private ITransactionDetailPresenter presenter;

    public ITransactionDetailPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TransactionDetailPresenter(getActivity());
        }
        return presenter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        //if (getArguments() != null && getArguments().containsKey("transaction")) {
            edit1 = view.findViewById(R.id.edit1);
            edit2 = view.findViewById(R.id.edit2);
            edit3 = view.findViewById(R.id.edit3);
            edit4 = view.findViewById(R.id.edit4);
            edit5 = view.findViewById(R.id.edit5);
            edit6 = view.findViewById(R.id.edit6);
            edit7 = view.findViewById(R.id.edit7);
            btnDelete = view.findViewById(R.id.btnDelete);
            btnSave = view.findViewById(R.id.btnSave);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (getArguments() != null && getArguments().containsKey("transaction")) {
                getPresenter().setTransaction(getArguments().getParcelable("transaction"));
                final Transaction transaction = getPresenter().getTransaction();
                selectedTransaction = transaction;
                //refresh=(RefreshList)getActivity();
                //needToRefresh=(NeedToRefresh)getActivity();





                edit5.setText(formatter.format(transaction.getDate()));
                edit2.setText("" + transaction.getAmount());
                edit1.setText(transaction.getTitle());
                edit3.setText(transaction.getType().toString());
                edit4.setText(transaction.getItemDescription());
                edit6.setText("" + transaction.getTransactionInterval());
                if (transaction.getEndDate() == null) edit7.setText("");
                else edit7.setText(formatter.format(transaction.getEndDate()));
            }else{
                selectedTransaction=null;
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                edit4.setText("");
                edit5.setText("");
                edit6.setText("");
                edit7.setText("");
            }


            edit1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit1.setBackgroundColor(Color.GREEN);

                }
            });

            edit2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit2.setBackgroundColor(Color.GREEN);

                }
            });

            edit3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit3.setBackgroundColor(Color.GREEN);

                }
            });

            edit4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit4.setBackgroundColor(Color.GREEN);

                }
            });

            edit5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit5.setBackgroundColor(Color.GREEN);

                }
            });

            edit6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit6.setBackgroundColor(Color.GREEN);

                }
            });

            edit7.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit7.setBackgroundColor(Color.GREEN);

                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Date dateHelp = formatter.parse(edit5.getText().toString());
                        double amountHelp = Double.parseDouble(edit2.getText().toString());
                        String titleHelp = edit1.getText().toString();
                        Transaction.Type typeHelp = Transaction.Type.gimmeType(edit3.getText().toString());
                        String descriptionHelp = edit4.getText().toString();
                        int intervalHelp;
                        Date endDateHelp;
                        if (typeHelp.equals(Transaction.Type.REGULARPAYMENT) || typeHelp.equals(Transaction.Type.REGULARINCOME)) {
                            intervalHelp = Integer.parseInt(edit6.getText().toString());
                            endDateHelp = formatter.parse(edit7.getText().toString());
                        } else {
                            intervalHelp = 0;
                            endDateHelp = null;
                        }
                        final Transaction newTransaction = new Transaction(dateHelp, amountHelp, titleHelp, typeHelp, descriptionHelp, intervalHelp, endDateHelp);

                        ArrayList<Transaction> helpModel = new ArrayList<>();
                        helpModel.addAll(TransactionModel.trans);
                        helpModel.add(newTransaction);
                        Calendar helpCalendar = Calendar.getInstance();
                        helpCalendar.setTime(newTransaction.getDate());
                        if (account.testMonthlyLimit(helpModel, helpCalendar.get(Calendar.MONTH), account.getMonthLimit())) {
                            AlertDialog alert = new AlertDialog.Builder(getActivity()).setTitle("Warning!!!!!!!").setMessage("This will make you go over the monthly limit\nAre you sure you want to do that?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            TransactionModel.trans.remove(selectedTransaction);
                                            TransactionModel.trans.add(newTransaction);
                                            if(selectedTransaction==null){
                                                selectedTransaction=null;
                                                edit1.setText("");
                                                edit1.setBackgroundColor(0);
                                                edit2.setText("");
                                                edit2.setBackgroundColor(0);
                                                edit3.setText("");
                                                edit3.setBackgroundColor(0);
                                                edit4.setText("");
                                                edit4.setBackgroundColor(0);
                                                edit5.setText("");
                                                edit5.setBackgroundColor(0);
                                                edit6.setText("");
                                                edit6.setBackgroundColor(0);
                                                edit7.setText("");
                                                edit7.setBackgroundColor(0);
                                            }


                                            //finish();
                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();

                        } else {
                            if(selectedTransaction!=null) {
                                TransactionModel.trans.remove(selectedTransaction);


                            }
                            TransactionModel.trans.add(newTransaction);
                            selectedTransaction=newTransaction;
                            if(selectedTransaction==null){
                                selectedTransaction=null;
                                edit1.setText("");
                                edit1.setBackgroundColor(0);
                                edit2.setText("");
                                edit2.setBackgroundColor(0);
                                edit3.setText("");
                                edit3.setBackgroundColor(0);
                                edit4.setText("");
                                edit4.setBackgroundColor(0);
                                edit5.setText("");
                                edit5.setBackgroundColor(0);
                                edit6.setText("");
                                edit6.setBackgroundColor(0);
                                edit7.setText("");
                                edit7.setBackgroundColor(0);
                            }

                            //finish();
                        }
                        //needToRefresh.refreshList();


                    } catch (Exception e) {
                        final AlertDialog alert = new AlertDialog.Builder(getActivity()).setTitle("Warning").setMessage("Invalid input!").
                                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                    }

                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedTransaction!=null) {
                        try {


                            AlertDialog alert = new AlertDialog.Builder(getActivity()).setTitle("Warning!!!!!!!").setMessage("Are you sure you want to delete it???")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            TransactionModel.trans.remove(selectedTransaction);


                                            selectedTransaction=null;
                                            edit1.setText("");
                                            edit1.setBackgroundColor(0);
                                            edit2.setText("");
                                            edit2.setBackgroundColor(0);
                                            edit3.setText("");
                                            edit3.setBackgroundColor(0);
                                            edit4.setText("");
                                            edit4.setBackgroundColor(0);
                                            edit5.setText("");
                                            edit5.setBackgroundColor(0);
                                            edit6.setText("");
                                            edit6.setBackgroundColor(0);
                                            edit7.setText("");
                                            edit7.setBackgroundColor(0);
                                            //needToRefresh.refreshList();
                                            //finish();
                                        }
                                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();


                        } catch (Exception e) {

                        }
                    }

                }


            });
        //}
        return view;
    }

}
