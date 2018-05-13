package com.hogwarts_eng_school.hogwarts_teacher.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAttendance {
    public enum Type {
        VISITED, VALID_SKIP, INVALID_SKIP
    }

    private Long id;
    private Long studentId;
    private Long time;
    private Type type;
}
