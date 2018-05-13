package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendanceInsertion;
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

            long attendanceId = studentAttendanceRest.addAttendance(
                    studentId,
                    StudentAttendanceInsertion.builder().type(type).time(time).build()
            );

            StudentAttendance studentAttendance = new StudentAttendance();
            {
                studentAttendance.setId(attendanceId);
                studentAttendance.setStudentId(studentId);
                studentAttendance.setTime(time);
                studentAttendance.setType(type);
            }

            studentAttendanceService.addAttendance(studentAttendance);

            listener.onSuccess(null);
        } catch (Exception e) {
            listener.onFailure();
        }
    }
}
