package com.hogwarts_eng_school.hogwarts_teacher.data;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Student {
    @NonNull private Long id;
    @NonNull private StudentStatusType statusType;
    @NonNull private List<Long> groupIds;
    @NonNull private String name;
    @NonNull private List<String> phones;
    @NonNull private List<String> emails;
}
