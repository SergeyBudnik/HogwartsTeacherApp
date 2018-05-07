package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class StudentsService {
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public List<Student> getGroupStudents(long groupId) {
        return Stream.of(students)
                .filter(student -> student.getGroupIds().contains(groupId))
                .toList();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
