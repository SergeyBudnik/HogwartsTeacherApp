package com.hogwarts_eng_school.hogwarts_teacher.data.group;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Group implements Serializable {
    @NonNull private Long id;
    @NonNull private Long cabinetId;
    @NonNull private List<Lesson> lessons;
}
