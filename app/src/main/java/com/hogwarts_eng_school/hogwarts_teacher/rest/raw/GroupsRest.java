package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.Group;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;

import java.util.List;

@Rest(rootUrl = "http://34.216.34.197:8080/HogwartsAPI", converters = JsonMapper.class)
public interface GroupsRest extends RestClientHeaders {
    @Get("/groups")
    @RequiresHeader(Headers.AUTHORIZATION)
    List<Group> getAllGroups();
}
