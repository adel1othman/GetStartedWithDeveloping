package com.example.android.getstartedwithdeveloping.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.android.getstartedwithdeveloping.data.SignUpContract.SignUpEntry;

/**
 * Created by Adel on 6/6/2017.
 */

public class SignUpDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = SignUpDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "SignUp.db";
    private static int DATABASE_VERSION = 1;

    public SignUpDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_REGISTER_TABLE =  "CREATE TABLE " + SignUpEntry.TABLE_NAME + " ("
                + SignUpEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SignUpEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + SignUpEntry.COLUMN_SURNAME + " TEXT NOT NULL, "
                + SignUpEntry.COLUMN_EMAIL + " TEXT NOT NULL, "
                + SignUpEntry.COLUMN_PHONE + " INTEGER, "
                + SignUpEntry.COLUMN_COURSE + " TEXT NOT NULL, "
                + SignUpEntry.COLUMN_DATE + " NUMERIC NOT NULL);";

        db.execSQL(SQL_CREATE_REGISTER_TABLE);
    }

    public Cursor readAllData() {

        String[] projection = {
                SignUpEntry._ID,
                SignUpEntry.COLUMN_NAME,
                SignUpEntry.COLUMN_SURNAME,
                SignUpEntry.COLUMN_EMAIL,
                SignUpEntry.COLUMN_PHONE,
                SignUpEntry.COLUMN_COURSE,
                SignUpEntry.COLUMN_DATE };

        Cursor cursor;
        cursor = getReadableDatabase().query(SignUpEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DATABASE_VERSION = newVersion;
    }
}