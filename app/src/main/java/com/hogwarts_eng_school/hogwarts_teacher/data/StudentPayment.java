package com.hogwarts_eng_school.hogwarts_teacher.data;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Builder;

@Builder
@Getter
public class StudentPayment implements Serializable {
    private Long id;
    @NonNull private Long amount;
    @NonNull private Long studentId;
    @NonNull private Long time;

    @JsonCreator
    public static StudentPayment create(
            @JsonProperty("id") Long id,
            @JsonProperty("amount") Long amount,
            @JsonProperty("studentId") Long studentId,
            @JsonProperty("time") Long time
    ) {
        return StudentPayment.builder()
                .id(id)
                .amount(amount)
                .studentId(studentId)
                .time(time)
                .build();
    }

    public StudentPayment withId(long id) {
        return StudentPayment.builder()
                .id(id)
                .amount(amount)
                .studentId(studentId)
                .time(time)
                .build();
    }
}
