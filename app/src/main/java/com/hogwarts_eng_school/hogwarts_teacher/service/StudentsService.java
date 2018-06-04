package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import static com.hogwarts_eng_school.hogwarts_teacher.data.StudentStatusType.GROUP_ASSIGNED;

@EBean(scope = EBean.Scope.Singleton)
public class StudentsService {
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public Optional<Student> getStudent(long id) {
        return Stream.of(students)
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public List<Student> getGroupStudents(long groupId) {
        return Stream.of(students)
                .filter(student -> student.getGroupIds().contains(groupId))
                .toList();
    }

    public void setStudents(List<Student> students) {
        this.students = Stream.of(students)
                .filter(it -> it.getStatusType() == GROUP_ASSIGNED)
                .toList();
    }
}
