package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendanceInsertion;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;

import java.util.List;

@Rest(rootUrl = "http://34.216.34.197:8080/HogwartsAPI", converters = JsonMapper.class)
public interface StudentAttendanceRest extends RestClientHeaders {
    @Get("/student-attendance")
    @RequiresHeader(Headers.AUTHORIZATION)
    List<StudentAttendance> getAllAttendances();

    @Post("/student-attendance/{studentId}")
    @RequiresHeader(Headers.AUTHORIZATION)
    Long addAttendance(
            @Path long studentId,
            @Body StudentAttendanceInsertion studentAttendanceInsertion
    );
}
