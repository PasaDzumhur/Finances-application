package com.example.rma20dzumhurpasa47.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TransactionDBOpeHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TransactionDataBase.db";
    public static final int DATABASE_VERSION = 2;


    public TransactionDBOpeHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TransactionDBOpeHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static final String TRANSACTION_TABLE = "transactions";
    public static final String TRANSACTION_INTERNAL_ID ="_id";
    public static final String TRANSACTION_ID = "id";
    public static final String TRANSACTION_TYPE_ID = "typeID";
    public static final String TRANSACTION_TITLE = "title";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_INTERVAL = "transactionInterval";
    public static final String TRANSACTION_END_DATE = "transactionEndDate";
    public static final String TRANSACTION_ITEM_DESCRIPTION = "itemDescription";
    public static final String TRANSACTION_TABLE_CREATE=
            "CREATE TABLE IF NOT EXISTS " + TRANSACTION_TABLE+" (" +
                    TRANSACTION_INTERNAL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TRANSACTION_ID + " INTEGER, " +
                    TRANSACTION_TITLE + " TEXT NOT NULL, " +
                    TRANSACTION_TYPE_ID + " INTEGER NOT NULL, "+
                    TRANSACTION_DATE + " TEXT NOT NULL, " +
                    TRANSACTION_AMOUNT + " INTEGER NOT NULL, "+
                    TRANSACTION_INTERVAL + " INTEGER, "+
                    TRANSACTION_ITEM_DESCRIPTION + " TEXT, "+
                    TRANSACTION_END_DATE + " TEXT);";
    public static final String TRANSACTION_DROP = "DROP TABLE IF EXISTS " + TRANSACTION_TABLE;




    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(TRANSACTION_DROP);
        db.execSQL(TRANSACTION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TRANSACTION_DROP);
        onCreate(db);

    }
}
