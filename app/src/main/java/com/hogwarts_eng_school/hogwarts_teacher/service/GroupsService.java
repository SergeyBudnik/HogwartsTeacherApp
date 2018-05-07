package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.hogwarts_eng_school.hogwarts_teacher.data.Group;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class GroupsService {
    private List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
