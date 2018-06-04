package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.student.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;

import java.util.List;

@Rest(rootUrl = RestConfiguration.ROOT, converters = JsonMapper.class)
public interface StudentAttendanceRest extends RestClientHeaders {
    @Get("/student-attendance")
    @RequiresHeader(Headers.AUTHORIZATION)
    List<StudentAttendance> getAllAttendances();

    @Post("/student-attendance")
    @RequiresHeader(Headers.AUTHORIZATION)
    Long addAttendance(@Body StudentAttendance studentAttendance);
}
