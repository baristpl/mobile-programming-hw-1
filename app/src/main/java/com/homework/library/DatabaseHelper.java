package com.homework.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CREATE =
            "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " (" +
                    BookContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BookContract.BookEntry.COLUMN_NAME + " TEXT, " +
                    BookContract.BookEntry.COLUMN_TITLE + " TEXT, " +
                    BookContract.BookEntry.COLUMN_COVER_IMAGE_URL + " TEXT, " +
                    BookContract.BookEntry.COLUMN_COVER_IMAGE_RES_ID + " TEXT, " +
                    BookContract.BookEntry.COLUMN_DESCRIPTION + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}