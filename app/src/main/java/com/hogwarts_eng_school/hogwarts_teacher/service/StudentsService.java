package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.dao.StudentsDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.student.Student;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import static com.hogwarts_eng_school.hogwarts_teacher.data.student.StudentStatusType.GROUP_ASSIGNED;

@EBean
public class StudentsService {
    @Bean
    StudentsDao studentDao;

    public List<Student> getStudents() {
        return studentDao.getStudents();
    }

    public Optional<Student> getStudent(long id) {
        return Stream.of(studentDao.getStudents())
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public List<Student> getGroupStudents(long groupId) {
        return Stream.of(studentDao.getStudents())
                .filter(student -> student.getGroupIds().contains(groupId))
                .toList();
    }

    public void setStudents(List<Student> students) {
        List<Student> activeStudents = Stream.of(students)
                .filter(it -> it.getStatusType() == GROUP_ASSIGNED)
                .toList();

        studentDao.setStudents(activeStudents);
    }
}
