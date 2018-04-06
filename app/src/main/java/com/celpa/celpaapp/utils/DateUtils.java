package com.celpa.celpaapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getFormattedString(int year, int month, int dayOfMonth) {
        String dateString = String.format("%s-%s-%s 00:00:00", year, month, dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(dateString);
            formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            return formatter.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "--";
    }
}
