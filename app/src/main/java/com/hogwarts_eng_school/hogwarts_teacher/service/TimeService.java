package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Optional;
import com.hogwarts_eng_school.hogwarts_teacher.data.DayOfWeek;

import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@EBean
public class TimeService {
    public Optional<DayOfWeek> getCurrentDay() {
        return DayOfWeek.fromIndex(getCalendar().get(Calendar.DAY_OF_WEEK));
    }

    public int getDate() {
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return getCalendar().get(Calendar.MONTH);
    }

    public int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    private Calendar getCalendar() {
        Date date = new Date();

        Calendar calendar = GregorianCalendar.getInstance();
        {
            calendar.setTime(date);
        }

        return calendar;
    }
}
