package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.StudentAttendanceRest;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentAttendanceService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Date;

@EBean
public class StudentAttendanceRestWrapper extends AbstractAuthWrapper {
    @Bean
    AuthService authService;
    @Bean
    StudentAttendanceService studentAttendanceService;

    @RestService
    StudentAttendanceRest studentAttendanceRest;

    @Background
    public void addAttendance(long studentId, StudentAttendance.Type type, RestListener<Void> listener) {
        try {
            listener.onStart();

            authenticateTo(
                    authService.getUserInfo().orElseThrow(RuntimeException::new).getAuthToken(),
                    studentAttendanceRest
            );

            long time = new Date().getTime();

            StudentAttendance studentAttendance = StudentAttendance.builder()
                    .studentId(studentId)
                    .time(time)
                    .type(type)
                    .build();

            long attendanceId = studentAttendanceRest.addAttendance(studentAttendance);

            studentAttendanceService.addAttendance(studentAttendance.withId(attendanceId));

            listener.onSuccess(null);
        } catch (Exception e) {
            listener.onFailure();
        }
    }
}
