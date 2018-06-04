package com.hogwarts_eng_school.hogwarts_teacher.data.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Builder;

@Builder
@Getter
public class AppUserInfo implements Serializable {
    @NonNull private Long id;
    @NonNull private String login;
    @NonNull private String name;
    @NonNull private String authToken;
}
