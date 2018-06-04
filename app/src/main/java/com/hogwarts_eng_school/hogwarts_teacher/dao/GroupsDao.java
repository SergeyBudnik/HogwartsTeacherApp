package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.Group;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class GroupsModel implements Serializable {
    private List<Group> groups = new ArrayList<>();
}

@EBean(scope = EBean.Scope.Singleton)
public class GroupsDao extends CommonDao<GroupsModel> {
    @RootContext
    Context context;

    public List<Group> getGroups() {
        readCache(context, getFileName(), false);

        return getValue().getGroups();
    }

    public void setGroups(List<Group> groups) {
        readCache(context, getFileName(), false);

        getValue().setGroups(groups);

        persist(context);
    }

    @Override
    protected GroupsModel newInstance() {
        return new GroupsModel();
    }

    @Override
    protected String getFileName() {
        return GroupsDao.class.getCanonicalName();
    }
}
