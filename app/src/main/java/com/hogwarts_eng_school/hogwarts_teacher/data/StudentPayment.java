package com.hogwarts_eng_school.hogwarts_teacher.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class StudentPayment {
    private Long id;
    @NonNull private Long amount;
    @NonNull private Long studentId;
    @NonNull private Long time;
}
