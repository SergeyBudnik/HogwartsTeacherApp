package com.hogwarts_eng_school.hogwarts_teacher.data.student;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Builder;

@Builder
@Getter
public class StudentAttendance implements Serializable {
    public enum Type {
        VISITED, VALID_SKIP, INVALID_SKIP
    }

    private Long id;
    @NonNull private Long studentId;
    @NonNull private Long time;
    @NonNull private Type type;

    @JsonCreator
    public static StudentAttendance create(
            @JsonProperty("id") Long id,
            @JsonProperty("studentId") Long studentId,
            @JsonProperty("time") Long time,
            @JsonProperty("type") Type type
    ) {
        return StudentAttendance.builder()
                .id(id)
                .studentId(studentId)
                .time(time)
                .type(type)
                .build();
    }

    public StudentAttendance withId(long id) {
        return StudentAttendance.builder()
                .id(id)
                .studentId(studentId)
                .time(time)
                .type(type)
                .build();
    }
}
