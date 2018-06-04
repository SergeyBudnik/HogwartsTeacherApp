package com.hogwarts_eng_school.hogwarts_teacher.data.common;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.R;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    MONDAY(2, R.string.monday_short),
    TUESDAY(3, R.string.tuesday_short),
    WEDNESDAY(4, R.string.wednesday_short),
    THURSDAY(5, R.string.thursday_short),
    FRIDAY(6, R.string.friday_short),
    SATURDAY(7, R.string.saturday_short),
    SUNDAY(1, R.string.sunday_short);

    private int index;
    private int shortNameId;

    DayOfWeek(int index, int shortNameId) {
        this.index = index;
        this.shortNameId = shortNameId;
    }

    public static Optional<DayOfWeek> fromIndex(int index) {
        return Stream
                .of(values())
                .filter(it -> it.index == index)
                .findFirst();
    }
}
