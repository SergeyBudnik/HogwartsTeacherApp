package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.student.StudentPayment;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class StudentPaymentModel implements Serializable {
    private List<StudentPayment> studentPayments = new ArrayList<>();
}

@EBean(scope = EBean.Scope.Singleton)
public class StudentPaymentDao extends CommonDao<StudentPaymentModel> {
    @RootContext
    Context context;

    public List<StudentPayment> getStudentPayment() {
        readCache(context, getFileName(), false);

        return getValue().getStudentPayments();
    }

    public void setStudentPayments(List<StudentPayment> studentPayments) {
        readCache(context, getFileName(), false);

        getValue().setStudentPayments(studentPayments);

        persist(context);
    }

    public void addStudentPayment(StudentPayment studentPayment) {
        readCache(context, getFileName(), false);

        getValue().getStudentPayments().add(studentPayment);

        persist(context);
    }

    @Override
    protected StudentPaymentModel newInstance() {
        return new StudentPaymentModel();
    }

    @Override
    protected String getFileName() {
        return StudentPaymentDao.class.getCanonicalName();
    }
}
