package com.example.backendcollect.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String getDateString(Date date) {
        return df.format(date);
    }

    public static Date toDate(String dateString) throws ParseException {
        return df.parse(dateString);
    }

    public static int getDayDiffer(Date startDate, Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long startDateTime = 0;
        try {
            startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endDateTime = 0;
        try {
            endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
    }
}
