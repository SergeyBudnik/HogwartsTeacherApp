package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPayment;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPaymentInsertion;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.StudentPaymentRest;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentPaymentService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Date;

@EBean
public class StudentPaymentRestWrapper extends AbstractAuthWrapper {
    @Bean
    AuthService authService;
    @Bean
    StudentPaymentService studentPaymentService;

    @RestService
    StudentPaymentRest studentPaymentRest;

    @Background
    public void addPayment(long studentId, long amount, RestListener<Void> listener) {
        try {
            listener.onStart();

            authenticateTo(
                    authService.getUserInfo().orElseThrow(RuntimeException::new).getAuthToken(),
                    studentPaymentRest
            );

            long time = new Date().getTime();

            long paymentId = studentPaymentRest.addPayment(
                    studentId,
                    StudentPaymentInsertion.builder().amount(amount).time(time).build()
            );

            StudentPayment studentPayment = new StudentPayment();
            {
                studentPayment.setId(paymentId);
                studentPayment.setStudentId(studentId);
                studentPayment.setTime(time);
                studentPayment.setAmount(amount);
            }

            studentPaymentService.addPayment(studentPayment);

            listener.onSuccess(null);
        } catch (Exception e) {
            listener.onFailure();
        }
    }
}
