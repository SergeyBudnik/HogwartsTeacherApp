package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.auth.AuthInfo;
import com.hogwarts_eng_school.hogwarts_teacher.data.auth.LoginCredentials;
import com.hogwarts_eng_school.hogwarts_teacher.data.auth.UserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;

@Rest(rootUrl = "http://194.58.42.198:8080/MunicipaliSecurityServer/user", converters = JsonMapper.class)
public interface LoginRest {
    @Post("")
    AuthInfo login(
            @Body LoginCredentials loginCredentials
    );

    @Get("/{authToken}")
    UserInfo getUserInfo(
            @Path String authToken
    );
}
