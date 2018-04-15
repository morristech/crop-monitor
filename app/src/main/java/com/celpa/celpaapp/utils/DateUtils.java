package com.celpa.celpaapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date getDate(int year, int month, int dayOfMonth) {
        String dateString = String.format("%s-%s-%s 00:00:00", year, month, dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date getDate(int year, int month, int dayOfMonth, int hour, int minute) {
        String dateString = String.format("%s-%s-%s %s:%s:00", year, month, dayOfMonth, hour, minute);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getFormattedString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm a");
        return formatter.format(date.getTime());
    }

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

    public static String getFormattedString(int year, int month, int dayOfMonth, int hour, int minute) {
        String dateString = String.format("%s-%s-%s %s:%s:00", year, month, dayOfMonth, hour, minute);
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

    public static String getFormattedString(long unixTimestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm a");
        Date date = new Date(unixTimestamp * 1000);
        return formatter.format(date.getTime());
    }
}
