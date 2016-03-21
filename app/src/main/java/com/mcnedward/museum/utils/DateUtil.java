package com.mcnedward.museum.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Edward on 3/20/2016.
 */
public class DateUtil {
    public static String PRETTY_DATE = "dd/MM/yyyy";
    public static String DATABASE_DATE = "yyyy-MM-dd";
    public static String NUMBER_DATE = "yyyyMMdd";

    /**
     * Gets a date stamp in the database format (yyyyy-MM-dd). If the app is in Edit Mode, then this datestamp will use the MainActivity's calendar.
     *
     * @return The date stamp in database format.
     */
    public static String getDatabaseDateStamp() {
        Date date = getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATABASE_DATE);
        return simpleDateFormat.format(date);
    }

    /**
     * Get the specified date in the pretty date format (dd/MM/yyyy).
     *
     * @param date The date to convert to pretty date format.
     * @return The date.
     */
    public static String getCalendarPrettyDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PRETTY_DATE);
        return simpleDateFormat.format(date);
    }

    public static String getCalendarPrettyDate() {
        Date currentDate = getDate();
        return getCalendarPrettyDate(currentDate);
    }

    /**
     * Converts a database format (yyyy-MM-dd) date to a pretty format (dd/MM/yyyy) date.
     *
     * @param date The date to convert.
     * @return The date in pretty format.
     */
    public static String getPrettyDateFromDatabaseDate(String date) {
        return convertDate(date, DATABASE_DATE, PRETTY_DATE);
    }

    /**
     * Converts a pretty format (dd/MM/yyyy) date to a database format (yyyy-MM-dd) date.
     *
     * @param date The date to convert.
     * @return The date in database format.
     */
    public static String getDatabaseDateFromPrettyDate(String date) {
        return convertDate(date, PRETTY_DATE, DATABASE_DATE);
    }

    public static String getDateFromRange(int dateRange, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1 * dateRange);
        Date previousDate = calendar.getTime();
        return new SimpleDateFormat(format).format(previousDate);
    }

    public static String getNumberDateStamp() {
        Date date = getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NUMBER_DATE);
        return simpleDateFormat.format(date);
    }

    public static int getDateAsNumber(String date) {
        String datestamp = convertDate(date, DATABASE_DATE, NUMBER_DATE);
        return Integer.parseInt(datestamp);
    }

    public static String getDateFromNumber(double date) {
        String datestamp = convertDate(String.valueOf(date), NUMBER_DATE, PRETTY_DATE);
        return datestamp;
    }

    public static Date getDate() {
        return new Date();
    }

    public static String convertDate(String date, String fromFormat, String toFormat) {
        SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
        String d = null;
        try {
            Date theDate = fromDateFormat.parse(date);
            SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat);
            d = toDateFormat.format(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
