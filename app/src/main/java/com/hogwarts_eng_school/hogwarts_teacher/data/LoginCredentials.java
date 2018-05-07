package com.hogwarts_eng_school.hogwarts_teacher.data;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class LoginCredentials {
    private String login;
    private String password;
}
