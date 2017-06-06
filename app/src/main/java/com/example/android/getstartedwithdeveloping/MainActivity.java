package com.example.android.getstartedwithdeveloping;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.getstartedwithdeveloping.data.SignUpContract.SignUpEntry;
import com.example.android.getstartedwithdeveloping.data.SignUpDbHelper;

public class MainActivity extends AppCompatActivity {

    LinearLayout signUpLay;
    private EditText editTextName, editTextSurname, editTextEmail, editTextPhone;
    private Spinner spinnerCourses, spinnerDates;
    private Button btnSignUp, btnShowTable;

    String mCourse, mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpLay = (LinearLayout)findViewById(R.id.signUpLayout);
        editTextName = (EditText)findViewById(R.id.edtTxtName);
        editTextSurname = (EditText)findViewById(R.id.edtTxtSurname);
        editTextEmail = (EditText)findViewById(R.id.edtTxtEmail);
        editTextPhone = (EditText)findViewById(R.id.edtTxtPhone);
        spinnerCourses = (Spinner)findViewById(R.id.spnrCourses);
        spinnerDates = (Spinner)findViewById(R.id.spnrDates);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnShowTable = (Button)findViewById(R.id.btnShowTable);

        signUpLay.setVisibility(View.GONE);
        setupSpinner();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = editTextName.getText().toString().trim();
                String surnameString = editTextSurname.getText().toString().trim();
                String emailString = editTextEmail.getText().toString().trim();
                int phone = 0;
                try {
                    phone = Integer.parseInt(editTextPhone.getText().toString().trim());
                }catch (NumberFormatException ex){
                    Log.e("Phone", "Problem parsing Phone feild", ex);
                }

                SignUpDbHelper mDbHelper = new SignUpDbHelper(getBaseContext());

                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(SignUpEntry.COLUMN_NAME, nameString);
                values.put(SignUpEntry.COLUMN_SURNAME, surnameString);
                values.put(SignUpEntry.COLUMN_EMAIL, emailString);
                values.put(SignUpEntry.COLUMN_PHONE, phone);
                values.put(SignUpEntry.COLUMN_COURSE, mCourse);
                values.put(SignUpEntry.COLUMN_DATE, mDate);

                long newRowId = db.insert(SignUpEntry.TABLE_NAME, null, values);

                if (newRowId == -1) {
                    Toast.makeText(getBaseContext(), "Error with your register", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Register saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter coursesSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_courses, android.R.layout.simple_spinner_item);

        coursesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerCourses.setAdapter(coursesSpinnerAdapter);

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                signUpLay.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(selection) && !selection.equals(getString(R.string.courses))) {
                    signUpLay.setVisibility(View.VISIBLE);
                    if (selection.equals(getString(R.string.android))) {
                        mCourse = getString(R.string.android);
                        ArrayAdapter andDatesSpinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.array_androidDates, android.R.layout.simple_spinner_item);
                        andDatesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinnerDates.setAdapter(andDatesSpinnerAdapter);
                    } else if (selection.equals(getString(R.string.vr))) {
                        mCourse = getString(R.string.vr);
                        ArrayAdapter vrDatesSpinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.array_vrDates, android.R.layout.simple_spinner_item);
                        vrDatesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinnerDates.setAdapter(vrDatesSpinnerAdapter);
                    } else {
                        mCourse = getString(R.string.robotics);
                        ArrayAdapter robDatesSpinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.array_roboticsDates, android.R.layout.simple_spinner_item);
                        robDatesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinnerDates.setAdapter(robDatesSpinnerAdapter);
                    }

                    spinnerDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mDate = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else {
                    signUpLay.setVisibility(View.GONE);
                    ArrayAdapter emptyDateSpinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.array_emptyDate, android.R.layout.simple_spinner_item);
                    spinnerDates.setAdapter(emptyDateSpinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
