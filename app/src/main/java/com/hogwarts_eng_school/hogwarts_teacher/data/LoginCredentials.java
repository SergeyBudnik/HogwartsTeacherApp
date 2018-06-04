package com.hogwarts_eng_school.hogwarts_teacher.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Builder;

@Builder
@Getter
public class LoginCredentials {
    @NonNull private String login;
    @NonNull private String password;
}
