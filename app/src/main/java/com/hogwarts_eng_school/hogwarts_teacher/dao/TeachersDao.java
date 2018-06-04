package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.teacher.Teacher;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class TeachersModel implements Serializable {
    private List<Teacher> teachers = new ArrayList<>();
}

@EBean(scope = EBean.Scope.Singleton)
public class TeachersDao extends CommonDao<TeachersModel> {
    @RootContext
    Context context;

    public List<Teacher> getTeachers() {
        readCache(context, getFileName(), false);

        return getValue().getTeachers();
    }

    public void setTeachers(List<Teacher> teachers) {
        readCache(context, getFileName(), false);

        getValue().setTeachers(teachers);

        persist(context);
    }

    @Override
    protected TeachersModel newInstance() {
        return new TeachersModel();
    }

    @Override
    protected String getFileName() {
        return TeachersDao.class.getCanonicalName();
    }
}
