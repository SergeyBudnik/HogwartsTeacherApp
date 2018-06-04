package com.hogwarts_eng_school.hogwarts_teacher.data;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class StudentPaymentInsertion {
    private Long time;
    private Long amount;
}
