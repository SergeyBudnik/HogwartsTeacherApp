package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.hogwarts_eng_school.hogwarts_teacher.dao.GroupsDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.Group;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class GroupsService {
    @Bean
    GroupsDao groupsDao;

    public List<Group> getGroups() {
        return groupsDao.getGroups();
    }

    public void setGroups(List<Group> groups) {
        groupsDao.setGroups(groups);
    }
}
