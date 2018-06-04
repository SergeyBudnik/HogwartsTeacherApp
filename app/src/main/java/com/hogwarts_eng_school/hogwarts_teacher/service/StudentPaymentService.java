package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.dao.StudentPaymentDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPayment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class StudentPaymentService {
    @Bean
    StudentPaymentDao studentPaymentDao;

    public List<StudentPayment> getStudentPayments(long studentId) {
        return Stream.of(studentPaymentDao.getStudentPayment())
                .filter(it -> it.getStudentId().equals(studentId))
                .toList();
    }

    public void setPayments(List<StudentPayment> payments) {
        studentPaymentDao.setStudentPayments(payments);
    }

    public void addPayment(StudentPayment payment) {
        studentPaymentDao.addStudentPayment(payment);
    }
}
