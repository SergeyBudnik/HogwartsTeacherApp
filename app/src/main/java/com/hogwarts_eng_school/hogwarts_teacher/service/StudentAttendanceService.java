package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class StudentAttendanceService {
    private List<StudentAttendance> attendances = new ArrayList<>();

    public Optional<StudentAttendance> getAttendance(long studentId) {
        Calendar cal = Calendar.getInstance();
        {
            cal.clear(Calendar.HOUR);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
        }

        long todayTime = cal.getTimeInMillis();

        return Stream.of(attendances)
                .filter(it -> Objects.equals(studentId, it.getStudentId()))
                .filter(it -> it.getTime() >= todayTime)
                .max((a1, a2) -> Objects.compareLong(a1.getTime(), a2.getTime()));
    }

    public void setAttendances(List<StudentAttendance> attendances) {
        this.attendances = attendances;
    }

    public void addAttendance(StudentAttendance attendance) {
        attendances.add(attendance);
    }
}
