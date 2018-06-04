package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.hogwarts_eng_school.hogwarts_teacher.data.auth.AppUserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.CabinetsRest;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.GroupsRest;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.StudentAttendanceRest;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.StudentPaymentRest;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.StudentsRest;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.TeacherRest;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.CabinetsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.GroupsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentAttendanceService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentPaymentService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@EBean
public class SchoolDataRestWrapper extends AbstractAuthWrapper {
    @RestService
    GroupsRest groupsRest;
    @RestService
    StudentsRest studentsRest;
    @RestService
    CabinetsRest cabinetsRest;
    @RestService
    TeacherRest teacherRest;
    @RestService
    StudentAttendanceRest studentAttendanceRest;
    @RestService
    StudentPaymentRest studentPaymentRest;

    @Bean
    AuthService authService;
    @Bean
    GroupsService groupsService;
    @Bean
    StudentsService studentsService;
    @Bean
    CabinetsService cabinetsService;
    @Bean
    TeachersService teachersService;
    @Bean
    StudentAttendanceService studentAttendanceService;
    @Bean
    StudentPaymentService studentPaymentService;

    @Background
    public void load(RestListener<Void> listener) {
        try {
            listener.onStart();

            authenticateTo(
                    authService.getUserInfo().map(AppUserInfo::getAuthToken).orElseThrow(RuntimeException::new),
                    groupsRest, studentsRest, cabinetsRest, teacherRest, studentAttendanceRest, studentPaymentRest
            );

            groupsService.setGroups(groupsRest.getAllGroups());
            studentsService.setStudents(studentsRest.getAllStudents());
            cabinetsService.setCabinets(cabinetsRest.getAllCabinets());
            teachersService.setTeachers(teacherRest.getTeachers());
            studentAttendanceService.setAttendances(studentAttendanceRest.getAllAttendances());
            studentPaymentService.setPayments(studentPaymentRest.getAllPayments());

            listener.onSuccess(null);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                listener.onAuthFailure();
            } else {
                listener.onGeneralFailure();
            }

            listener.onFailure();
        } catch (Exception e) {
            listener.onGeneralFailure();
            listener.onFailure();
        }
    }
}
