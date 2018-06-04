package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPayment;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class StudentPaymentService {
    private List<StudentPayment> payments;

    public List<StudentPayment> getStudentPayments(long studentId) {
        return Stream.of(payments)
                .filter(it -> it.getStudentId().equals(studentId))
                .toList();
    }

    public void setPayments(List<StudentPayment> payments) {
        this.payments = payments;
    }

    public void addPayment(StudentPayment payment) {
        payments.add(payment);
    }
}
