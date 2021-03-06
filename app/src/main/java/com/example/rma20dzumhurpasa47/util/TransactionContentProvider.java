package com.example.rma20dzumhurpasa47.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TransactionContentProvider extends ContentProvider {

    private static final int ALLROWS = 1;
    private static final int ONEROW = 2;
    private static final UriMatcher uM;

    static {
        uM = new UriMatcher(UriMatcher.NO_MATCH);
        uM.addURI("rma.provider.transactions","elements",ALLROWS);
        uM.addURI("rma.provider.transactions","elements/#",ONEROW);
    }

    TransactionDBOpeHelper tHelper;
    @Override
    public boolean onCreate() {
        tHelper = new TransactionDBOpeHelper(getContext(),TransactionDBOpeHelper.DATABASE_NAME,
                null,TransactionDBOpeHelper.DATABASE_VERSION);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database;
        try{
            database = tHelper.getWritableDatabase();

        }catch(SQLiteException e){
            database = tHelper.getReadableDatabase();
        }
        String groupby=null;
        String having = null;
        SQLiteQueryBuilder squery = new SQLiteQueryBuilder();

        switch (uM.match(uri)){
            case ONEROW:
                String idRow = uri.getPathSegments().get(0);
                squery.appendWhere(TransactionDBOpeHelper.TRANSACTION_ID + "="+idRow);
            default: break;
        }
        squery.setTables(TransactionDBOpeHelper.TRANSACTION_TABLE);
        Cursor cursor = squery.query(database,projection,selection,selectionArgs,groupby,having,sortOrder);
        return  cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uM.match(uri)){
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.rma.elemental";
            case ONEROW:
                return "vnd.android.cursor.item/vnd.rma.elemental";
            default:
                throw new IllegalArgumentException("Unsuported uri: "+uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database ;
        try{
            database = tHelper.getWritableDatabase();

        }catch(SQLiteException e){
            database = tHelper.getReadableDatabase();
        }
        long id = database.insert(TransactionDBOpeHelper.TRANSACTION_TABLE,null,values);
        return uri.buildUpon().appendPath(String.valueOf(id)).build();


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count =0;
        SQLiteDatabase database;
        try{
            database = tHelper.getWritableDatabase();

        }catch(SQLiteException e){
            database = tHelper.getReadableDatabase();
        }
        SQLiteQueryBuilder squery = new SQLiteQueryBuilder();
        switch(uM.match(uri)){
            case ONEROW:
                String idRow = uri.getPathSegments().get(0);
                String where = TransactionDBOpeHelper.TRANSACTION_ID + "="+idRow;
                count = database.delete(TransactionDBOpeHelper.TRANSACTION_TABLE,where,selectionArgs);
                break;
            case ALLROWS:
                count = database.delete(TransactionDBOpeHelper.TRANSACTION_TABLE,selection,selectionArgs);
            default: break;
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase database;
        try{
            database = tHelper.getWritableDatabase();

        }catch(SQLiteException e){
            database = tHelper.getReadableDatabase();
        }
        SQLiteQueryBuilder squery = new SQLiteQueryBuilder();
        switch(uM.match(uri)){
            case ONEROW:
                String idRow = uri.getLastPathSegment();
                String where = TransactionDBOpeHelper.TRANSACTION_ID + "="+idRow;
                count = database.update(TransactionDBOpeHelper.TRANSACTION_TABLE,values,where,selectionArgs);
                break;
            case ALLROWS:
                count = database.update(TransactionDBOpeHelper.TRANSACTION_TABLE,values,selection,selectionArgs);
            default: break;
        }

        return count;
    }
}
