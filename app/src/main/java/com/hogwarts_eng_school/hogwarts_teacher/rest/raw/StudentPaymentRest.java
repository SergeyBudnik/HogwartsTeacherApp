package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPayment;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPaymentInsertion;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;

import java.util.List;

@Rest(rootUrl = RestConfiguration.ROOT, converters = JsonMapper.class)
public interface StudentPaymentRest extends RestClientHeaders {
    @Get("/student-payment")
    @RequiresHeader(Headers.AUTHORIZATION)
    List<StudentPayment> getAllPayments();

    @Post("/student-payment/{studentId}")
    @RequiresHeader(Headers.AUTHORIZATION)
    Long addPayment(
            @Path long studentId,
            @Body StudentPaymentInsertion studentPaymentInsertion
    );
}
