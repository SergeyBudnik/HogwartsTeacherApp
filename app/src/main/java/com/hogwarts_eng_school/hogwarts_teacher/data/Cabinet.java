package com.hogwarts_eng_school.hogwarts_teacher.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Cabinet {
    @NonNull private Long id;
    @NonNull private String name;
}
