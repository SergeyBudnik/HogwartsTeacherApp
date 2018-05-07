package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.Headers;

import org.androidannotations.rest.spring.api.RestClientHeaders;

abstract class AbstractAuthWrapper {
    protected void authenticateTo(String authHeader, RestClientHeaders... rests) {
        Stream.of(rests).forEach(rest -> rest.setHeader(Headers.AUTHORIZATION, authHeader));
    }
}
