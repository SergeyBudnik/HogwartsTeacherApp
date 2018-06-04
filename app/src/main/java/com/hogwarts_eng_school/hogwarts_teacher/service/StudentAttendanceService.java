package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.dao.StudentAttendanceDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.student.StudentAttendance;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.List;

@EBean
public class StudentAttendanceService {
    @Bean
    StudentAttendanceDao studentAttendanceDao;

    public Optional<StudentAttendance> getAttendance(long studentId) {
        Calendar cal = Calendar.getInstance();
        {
            cal.clear(Calendar.HOUR);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
        }

        long todayTime = cal.getTimeInMillis();

        return Stream.of(studentAttendanceDao.getStudentAttendances())
                .filter(it -> Objects.equals(studentId, it.getStudentId()))
                .filter(it -> it.getTime() >= todayTime)
                .max((a1, a2) -> Objects.compareLong(a1.getTime(), a2.getTime()));
    }

    public void setAttendances(List<StudentAttendance> attendances) {
        studentAttendanceDao.setStudentAttendances(attendances);
    }

    public void addAttendance(StudentAttendance attendance) {
        studentAttendanceDao.addStudentAttendance(attendance);
    }
}
