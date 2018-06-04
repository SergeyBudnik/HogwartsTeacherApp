package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class StudentAttendanceModel implements Serializable {
    private List<StudentAttendance> studentAttendances = new ArrayList<>();
}

@EBean(scope = EBean.Scope.Singleton)
public class StudentAttendanceDao extends CommonDao<StudentAttendanceModel> {
    @RootContext
    Context context;

    public List<StudentAttendance> getStudentAttendances() {
        readCache(context, getFileName(), false);

        return getValue().getStudentAttendances();
    }

    public void setStudentAttendances(List<StudentAttendance> studentAttendances) {
        readCache(context, getFileName(), false);

        getValue().setStudentAttendances(studentAttendances);

        persist(context);
    }

    public void addStudentAttendance(StudentAttendance studentAttendance) {
        readCache(context, getFileName(), false);

        getValue().getStudentAttendances().add(studentAttendance);

        persist(context);
    }

    @Override
    protected StudentAttendanceModel newInstance() {
        return new StudentAttendanceModel();
    }

    @Override
    protected String getFileName() {
        return StudentAttendanceDao.class.getCanonicalName();
    }
}
