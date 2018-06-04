package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPayment;
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

            StudentPayment studentPayment = StudentPayment.builder()
                    .studentId(studentId)
                    .time(time)
                    .amount(amount)
                    .build();

            long paymentId = studentPaymentRest.addPayment(studentPayment);

            studentPaymentService.addPayment(studentPayment.withId(paymentId));

            listener.onSuccess(null);
        } catch (Exception e) {
            listener.onFailure();
        }
    }
}
