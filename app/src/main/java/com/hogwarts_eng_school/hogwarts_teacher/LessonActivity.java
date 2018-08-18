package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.adapter.StudentsListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.group.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.student.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.teacher.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.service.LessonsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.view.common.LoadableImageView;
import com.hogwarts_eng_school.hogwarts_teacher.view.app.menu.MenuView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;

@EActivity(R.layout.activity_lesson)
public class LessonActivity extends BaseActivity {
    public static final int VIEW_LESSON_ACTION_ID = 1;

    public static final String EXTRA_GROUP_ID = "groupId";
    public static final String EXTRA_LESSON_ID = "lessonId";

    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.content)
    View contentView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.teacher_icon)
    LoadableImageView teacherIconView;
    @ViewById(R.id.teacher)
    TextView teacherView;
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
    @Bean
    TeachersService teachersService;

    @Extra(EXTRA_GROUP_ID)
    long groupId;
    @Extra(EXTRA_LESSON_ID)
    long lessonId;

    @AfterViews
    void init() {
        initMenu(rootView, contentView);

        menuView.setCurrentPage(MenuView.Page.NONE);

        Lesson lesson = lessonsService.getLesson(lessonId).orElseThrow(RuntimeException::new);
        Teacher teacher = lessonsService.getTeacher(lessonId).orElseThrow(RuntimeException::new);

        teacherIconView.configure(
                "teacher",
                R.color.gray_very_light,
                () -> teachersService.getImage(teacher.getLogin()).orElseThrow(RuntimeException::new),
                () -> null
        );

        teacherView.setText(teacher.getName());

        cabinetView.setText(lessonsService.getCabinet(lessonId).orElseThrow(RuntimeException::new).getName());

        timeView.setText(getResources().getString(
                R.string.lesson_time,
                getResources().getString(lesson.getStartTime().getTranslationId()),
                getResources().getString(lesson.getFinishTime().getTranslationId())
        ));

        setStudents();
    }

    @Click(R.id.back)
    void onBackClick() {
        onClose();
    }

    @Click(R.id.add_lesson_status)
    void onAddLessonStatusClick() {
        redirectForResult(
                AddLessonStatusActivity_.class,
                R.anim.enter_open_anim,
                R.anim.exit_open_anim,
                AddLessonStatusActivity.ADD_LESSON_STATUS_ACTION_ID,
                Collections.emptyMap()
        );
    }

    @Override
    public void onBackPressed() {
        onClose();
    }

    @UiThread
    void setStudents() {
        studentsListAdapter.setItems(studentsService.getGroupStudents(groupId));
        studentsListAdapter.setOnStudentAttendanceClick(this::addAttendance);
        studentsListAdapter.setOnStudentPaymentClick(this::addPayment);

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

    private void addAttendance(Student student) {
        redirectForResult(
                AddStudentAttendanceActivity_.class,
                R.anim.enter_open_anim,
                R.anim.exit_open_anim,
                AddStudentAttendanceActivity.ADD_ATTENDANCE_ACTION_ID,
                Collections.singletonMap(AddStudentAttendanceActivity.EXTRA_STUDENT_ID, student.getId())
        );
    }

    private void addPayment(Student student) {
        redirectForResult(
                AddStudentPaymentActivity_.class,
                R.anim.enter_open_anim,
                R.anim.exit_open_anim,
                AddStudentPaymentActivity.ADD_PAYMENT_ACTION_ID,
                Collections.singletonMap(AddStudentPaymentActivity.EXTRA_STUDENT_ID, student.getId())
        );
    }

    private void onClose() {
        finishRedirectForResult(
                R.anim.enter_close_anim,
                R.anim.exit_close_anim,
                RESULT_OK
        );
    }
}
