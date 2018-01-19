package com.one.zyk.chickensoup.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static DateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormatD = new SimpleDateFormat("d");
    private static DateFormat dateFormatYYYYMMM = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

    private static DateFormat dateFormatYYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DateFormat dateFormatMMDDHHMM = new SimpleDateFormat("MM-dd HH:mm");

    private static DateFormat dateFormatMMDD = new SimpleDateFormat("MM-dd");

    private static DateFormat dateFormatHHMM = new SimpleDateFormat("HH:mm");

    private static DateFormat dateFormatYYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String formatDateASYYYYMMDD(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatYYYYMMDD.format(date);
    }

    public static String formatDateASD(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatD.format(date);
    }

    public static String formatDateASYYYYMMM(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatYYYYMMM.format(date);
    }


    public static String formatDateASYYYYMMDDHHMMSS(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatYYYYMMDDHHMMSS.format(date);
    }

    public static String formatDateASMMDDHHMM(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatMMDDHHMM.format(date);
    }

    public static String formatDateASMMDD(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatMMDD.format(date);
    }

    public static String formatDateASHHMM(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatHHMM.format(date);
    }

    public static String formatDateASYYYYMMDDHHMM(Date date) {

        if (date == null) {
            return null;
        }

        return dateFormatYYYYMMDDHHMM.format(date);
    }

    public static Date parseDataASYYYYMMDDHHMMSS(String date) {

        if (date == null) {
            return null;
        }

        try {
            return dateFormatYYYYMMDDHHMMSS.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDataASYYYYMMDD(String date) {

        if (date == null) {
            return null;
        }

        try {
            return dateFormatYYYYMMDD.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate(long time) {
        Date date = new Date(time);
        return date;

    }

}
