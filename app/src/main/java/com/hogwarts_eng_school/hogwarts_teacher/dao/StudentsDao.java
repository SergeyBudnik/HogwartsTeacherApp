package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.Student;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class StudentsModel {
    private List<Student> students = new ArrayList<>();
}

@EBean(scope = EBean.Scope.Singleton)
public class StudentsDao extends CommonDao<StudentsModel> {
    @RootContext
    Context context;

    public List<Student> getStudents() {
        readCache(context, getFileName(), false);

        return getValue().getStudents();
    }

    public void setStudents(List<Student> students) {
        readCache(context, getFileName(), false);

        getValue().setStudents(students);

        persist(context);
    }

    @Override
    protected StudentsModel newInstance() {
        return new StudentsModel();
    }

    @Override
    protected String getFileName() {
        return StudentsDao.class.getCanonicalName();
    }
}
