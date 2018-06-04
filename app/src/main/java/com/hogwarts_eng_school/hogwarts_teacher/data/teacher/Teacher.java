package com.hogwarts_eng_school.hogwarts_teacher.data.teacher;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher implements Serializable {
    private Long id;
    private String login;
    private String name;
    private List<String> phones;
}
