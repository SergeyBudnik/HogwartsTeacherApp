package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.adapter.StudentsListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.service.LessonsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentsService;
import com.hogwarts_eng_school.hogwarts_teacher.view.MenuView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;

@EActivity(R.layout.activity_group)
public class LessonActivity extends BaseActivity {
    public static final int VIEW_LESSON_ACTION_ID = 1;

    public static final String EXTRA_GROUP_ID = "groupId";
    public static final String EXTRA_LESSON_ID = "lessonId";

    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.cabinet)
    TextView cabinetView;
    @ViewById(R.id.time)
    TextView timeView;
    @ViewById(R.id.students)
    ListView studentsView;

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
        rootView.setScrimColor(Color.TRANSPARENT);

        menuView.setCurrentPage(MenuView.Page.NONE);

        cabinetView.setText(lessonsService.getCabinet(lessonId).orElseThrow(RuntimeException::new).getName());

        Lesson lesson = lessonsService.getLesson(lessonId).orElseThrow(RuntimeException::new);

        timeView.setText(getResources().getString(
                R.string.lesson_time,
                getResources().getString(lesson.getStartTime().getTranslationId()),
                getResources().getString(lesson.getFinishTime().getTranslationId())
        ));

        setStudents();
    }

    @Click(R.id.back)
    void onBackClick() {
        finishRedirectForResult(0, 0, RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishRedirectForResult(0, 0, RESULT_OK);
    }

    @UiThread
    void setStudents() {
        studentsListAdapter.setItems(studentsService.getGroupStudents(groupId));
        studentsListAdapter.setOnStudentAttendanceClick(this::showAttendancePopup);

        studentsView.setAdapter(studentsListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == AddStudentAttendanceActivity.ADD_ATTENDANCE_ACTION_ID) {
                setStudents();
            }
        }
    }

    private void showAttendancePopup(Student student) {
        redirectForResult(
                AddStudentAttendanceActivity_.class,
                0, 0,
                AddStudentAttendanceActivity.ADD_ATTENDANCE_ACTION_ID,
                Collections.singletonMap(AddStudentAttendanceActivity.EXTRA_STUDENT_ID, student.getId())
        );
    }
}
