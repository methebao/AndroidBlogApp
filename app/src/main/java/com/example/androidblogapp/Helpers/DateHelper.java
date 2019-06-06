package com.example.androidblogapp.Helpers;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public final class DateHelper {

    private static DateHelper INSTANCE = null;

    // other instance variables can be here

    private DateHelper() {};

    public static DateHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DateHelper();
        }
        return(INSTANCE);
    }

    public String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;


    }
}
