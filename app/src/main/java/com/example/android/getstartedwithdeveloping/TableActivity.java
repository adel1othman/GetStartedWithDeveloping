package com.example.android.getstartedwithdeveloping;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.getstartedwithdeveloping.data.SignUpContract.SignUpEntry;
import com.example.android.getstartedwithdeveloping.data.SignUpDbHelper;

/**
 * Created by Adel on 6/6/2017.
 */

public class TableActivity extends AppCompatActivity {

    private SignUpDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        mDbHelper = new SignUpDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                SignUpEntry._ID,
                SignUpEntry.COLUMN_NAME,
                SignUpEntry.COLUMN_SURNAME,
                SignUpEntry.COLUMN_EMAIL,
                SignUpEntry.COLUMN_PHONE,
                SignUpEntry.COLUMN_COURSE,
                SignUpEntry.COLUMN_DATE };

        Cursor cursor = db.query(
                SignUpEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.txtViewTable);

        try {
            displayView.setText("The register table contains " + cursor.getCount() + " registered people.\n\n");
            displayView.append(SignUpEntry._ID + " - " +
                    SignUpEntry.COLUMN_NAME + " - " +
                    SignUpEntry.COLUMN_SURNAME + " - " +
                    SignUpEntry.COLUMN_EMAIL + " - " +
                    SignUpEntry.COLUMN_PHONE + " - " +
                    SignUpEntry.COLUMN_COURSE + " - " +
                    SignUpEntry.COLUMN_DATE + "\n");

            int idColumnIndex = cursor.getColumnIndex(SignUpEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(SignUpEntry.COLUMN_NAME);
            int surnameColumnIndex = cursor.getColumnIndex(SignUpEntry.COLUMN_SURNAME);
            int emailColumnIndex = cursor.getColumnIndex(SignUpEntry.COLUMN_EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(SignUpEntry.COLUMN_PHONE);
            int courseColumnIndex = cursor.getColumnIndex(SignUpEntry.COLUMN_COURSE);
            int dateColumnIndex = cursor.getColumnIndex(SignUpEntry.COLUMN_DATE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentSurname = cursor.getString(surnameColumnIndex);
                String currentEmail = cursor.getString(emailColumnIndex);
                int currentPhone = cursor.getInt(phoneColumnIndex);
                String currentCourse = cursor.getString(courseColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentSurname + " - " +
                        currentEmail + " - " +
                        currentPhone + " - " +
                        currentCourse + " - " +
                        currentDate));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertNewRegister() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SignUpEntry.COLUMN_NAME, "Adel");
        values.put(SignUpEntry.COLUMN_SURNAME, "Othman");
        values.put(SignUpEntry.COLUMN_EMAIL, "adelothman@mail.com");
        values.put(SignUpEntry.COLUMN_PHONE, 911234567);
        values.put(SignUpEntry.COLUMN_COURSE, "Programmer - Web applications");
        values.put(SignUpEntry.COLUMN_DATE, "2018-01-01");

        long newRowId = db.insert(SignUpEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertNewRegister();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}