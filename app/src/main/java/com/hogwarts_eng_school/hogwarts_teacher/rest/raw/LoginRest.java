package com.hogwarts_eng_school.hogwarts_teacher.rest.raw;

import com.hogwarts_eng_school.hogwarts_teacher.data.AuthInfo;
import com.hogwarts_eng_school.hogwarts_teacher.data.LoginCredentials;
import com.hogwarts_eng_school.hogwarts_teacher.data.UserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.rest.mapper.JsonMapper;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;

@Rest(rootUrl = "http://34.216.34.197:8080/MunicipaliSecurityServer/user", converters = JsonMapper.class)
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
