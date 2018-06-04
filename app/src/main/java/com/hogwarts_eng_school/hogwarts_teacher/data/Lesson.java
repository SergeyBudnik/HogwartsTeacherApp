package com.hogwarts_eng_school.hogwarts_teacher.data;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Lesson implements Serializable {
    @NonNull private Long id;
    @NonNull private Long teacherId;
    @NonNull private DayOfWeek day;
    @NonNull private Time startTime;
    @NonNull private Time finishTime;
}
