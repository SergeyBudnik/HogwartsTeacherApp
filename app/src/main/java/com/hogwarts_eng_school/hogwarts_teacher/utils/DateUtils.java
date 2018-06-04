package com.hogwarts_eng_school.hogwarts_teacher.utils;

import com.hogwarts_eng_school.hogwarts_teacher.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static int getCurrentMonthString() {
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(new Date());

        int month = calendar.get(Calendar.MONTH);

        switch (month) {
            case 0:
                return R.string.january;
            case 1:
                return R.string.february;
            case 2:
                return R.string.march;
            case 3:
                return R.string.april;
            case 4:
                return R.string.may;
            case 5:
                return R.string.june;
            case 6:
                return R.string.july;
            case 7:
                return R.string.august;
            case 8:
                return R.string.september;
            case 9:
                return R.string.october;
            case 10:
                return R.string.november;
            case 11:
                return R.string.december;
            default:
                throw new RuntimeException("No suitable value for '" + month + "'");
        }
    }

    public static boolean isCurrentMonth(long time) {
        Calendar timeCalendar = getCalendar(time);
        Calendar currentCalendar = getCalendar(new Date().getTime());

        return timeCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH);
    }

    private static Calendar getCalendar(long time) {
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(new Date(time));

        return calendar;
    }
}
