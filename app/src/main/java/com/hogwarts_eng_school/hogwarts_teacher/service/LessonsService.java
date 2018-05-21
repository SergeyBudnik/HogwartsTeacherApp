package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;
import com.hogwarts_eng_school.hogwarts_teacher.data.DayOfWeek;
import com.hogwarts_eng_school.hogwarts_teacher.data.Group;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.Teacher;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean
public class LessonsService {
    @Bean
    GroupsService groupsService;
    @Bean
    StudentsService studentsService;
    @Bean
    CabinetsService cabinetsService;
    @Bean
    TeachersService teachersService;
    @Bean
    AuthService authService;

    @Bean
    TimeService timeService;

    public Optional<Lesson> getLesson(long id) {
        return Stream.of(groupsService.getGroups())
                .flatMap(it -> Stream.of(it.getLessons()))
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();
    }

    public List<Lesson> getMyLessons() {
        List<Lesson> res = new ArrayList<>();

        DayOfWeek currentDay = timeService.getCurrentDay().orElseThrow(RuntimeException::new);

        long teacherId = authService.getUserInfo().orElseThrow(RuntimeException::new).getId();

        for (Group group : groupsService.getGroups()) {
            for (Lesson lesson : group.getLessons()) {
                boolean dayMatches = lesson.getDay() == currentDay;
                boolean teacherMatches = Objects.equals(teacherId, lesson.getTeacherId());

                if (dayMatches && teacherMatches) {
                    res.add(lesson);
                }
            }
        }

        return Stream.of(res).sortBy(l -> l.getStartTime().getOrder()).toList();
    }

    public List<Lesson> getTodayLessons() {
        List<Lesson> res = new ArrayList<>();

        DayOfWeek currentDay = timeService.getCurrentDay().orElseThrow(RuntimeException::new);

        for (Group group : groupsService.getGroups()) {
            for (Lesson lesson : group.getLessons()) {
                if (lesson.getDay() == currentDay) {
                    res.add(lesson);
                }
            }
        }

        return res;
    }

    public Optional<Group> getGroup(long lessonId) {
        for (Group group : groupsService.getGroups()) {
            boolean groupMatches = Stream
                    .of(group.getLessons())
                    .anyMatch(lesson -> Objects.equals(lesson.getId(), lessonId));

            if (groupMatches) {
                return Optional.of(group);
            }
        }

        return Optional.empty();
    }

    public Optional<Cabinet> getCabinet(long lessonId) {
        return getGroup(lessonId)
                .map(it -> cabinetsService.getCabinet(it.getCabinetId()))
                .orElseThrow(RuntimeException::new);
    }

    public Optional<Teacher> getTeacher(long lessonId) {
        return getLesson(lessonId)
                .flatMap(it -> teachersService.getTeacher(it.getTeacherId()));
    }

    public Optional<List<Student>> getStudents(long lessonId) {
        Group group = getGroup(lessonId).orElseThrow(RuntimeException::new);

        return Optional.of(
                Stream.of(studentsService.getStudents())
                        .filter(it -> it.getGroupIds().contains(group.getId()))
                        .toList()
        );
    }
}
