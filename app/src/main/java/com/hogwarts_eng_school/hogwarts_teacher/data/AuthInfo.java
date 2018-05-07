package com.hogwarts_eng_school.hogwarts_teacher.data;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthInfo implements Serializable {
    private String token;
}
