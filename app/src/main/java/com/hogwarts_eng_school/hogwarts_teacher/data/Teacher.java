package com.hogwarts_eng_school.hogwarts_teacher.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher {
    private Long id;
    private String login;
    private String name;
    private List<String> phones;
}
