package com.hogwarts_eng_school.hogwarts_teacher.data.cabinet;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Cabinet implements Serializable {
    @NonNull private Long id;
    @NonNull private String name;
}
