package com.example.android.getstartedwithdeveloping.data;

import android.provider.BaseColumns;

/**
 * Created by Adel on 6/6/2017.
 */

public final class SignUpContract {

    private SignUpContract() {}

    public static final class SignUpEntry implements BaseColumns {

        public final static String TABLE_NAME = "registered";

        public final static String COLUMN_NAME ="name";
        public final static String COLUMN_SURNAME = "surname";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_PHONE = "phone";
        public final static String COLUMN_COURSE = "course";
        public final static String COLUMN_DATE = "date";
    }

}