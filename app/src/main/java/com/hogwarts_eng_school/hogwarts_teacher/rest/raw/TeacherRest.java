package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.teacher.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;

import java.util.List;

@Rest(rootUrl = RestConfiguration.ROOT, converters = JsonMapper.class)
public interface TeacherRest extends RestClientHeaders {
    @Get("/teachers")
    @RequiresHeader(Headers.AUTHORIZATION)
    List<Teacher> getTeachers();

    @Get("/teachers/login/{login}")
    @RequiresHeader(Headers.AUTHORIZATION)
    Teacher getTeacher(
            @Path String login
    );
}
