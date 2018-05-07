package com.hogwarts_eng_school.hogwarts_teacher;

import android.widget.ListView;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.adapter.StudentsListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.service.LessonsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentsService;
import com.hogwarts_eng_school.hogwarts_teacher.view.MenuView;
import com.hogwarts_eng_school.hogwarts_teacher.view.StudentAttendancePopupView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_group)
public class GroupActivity extends BaseActivity {
    public static final String EXTRA_GROUP_ID = "groupId";
    public static final String EXTRA_LESSON_ID = "lessonId";

    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.cabinet)
    TextView cabinetView;
    @ViewById(R.id.time)
    TextView timeView;
    @ViewById(R.id.students)
    ListView studentsView;

    @ViewById(R.id.attendance_popup)
    StudentAttendancePopupView studentAttendancePopupView;

    @Bean
    StudentsListAdapter studentsListAdapter;

    @Bean
    LessonsService lessonsService;
    @Bean
    StudentsService studentsService;

    @Extra(EXTRA_GROUP_ID)
    long groupId;
    @Extra(EXTRA_LESSON_ID)
    long lessonId;

    @AfterViews
    void init() {
        menuView.setCurrentPage(MenuView.Page.NONE);

        cabinetView.setText(lessonsService.getCabinet(lessonId).orElseThrow(RuntimeException::new).getName());

        Lesson lesson = lessonsService.getLesson(lessonId).orElseThrow(RuntimeException::new);

        timeView.setText(getResources().getString(
                R.string.lesson_time,
                getResources().getString(lesson.getStartTime().getTranslationId()),
                getResources().getString(lesson.getFinishTime().getTranslationId())
        ));

        studentsListAdapter.setItems(studentsService.getGroupStudents(groupId));
        studentsListAdapter.setOnStudentAttendanceClick(this::showAttendancePopup);

        studentsView.setAdapter(studentsListAdapter);
    }

    @Click(R.id.back)
    void onBackClick() {
        finish();
    }

    private void showAttendancePopup(long studentId) {
        studentAttendancePopupView.show();
    }
}
