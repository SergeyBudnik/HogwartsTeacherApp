package com.hogwarts_eng_school.hogwarts_teacher.data.student;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Builder;

@Builder
@Getter
public class Student implements Serializable {
    @NonNull private Long id;
    @NonNull private StudentStatusType statusType;
    @NonNull private List<Long> groupIds;
    @NonNull private String name;
    @NonNull private List<String> phones;
    @NonNull private List<String> emails;

    @JsonCreator
    public static Student create(
            @JsonProperty("id") Long id,
            @JsonProperty("statusType") StudentStatusType statusType,
            @JsonProperty("groupIds") List<Long> groupIds,
            @JsonProperty("name") String name,
            @JsonProperty("phones") List<String> phones,
            @JsonProperty("emails") List<String> emails
    ) {
        return Student.builder()
                .id(id)
                .statusType(statusType)
                .groupIds(groupIds)
                .name(name)
                .phones(phones)
                .emails(emails)
                .build();
    }
}
