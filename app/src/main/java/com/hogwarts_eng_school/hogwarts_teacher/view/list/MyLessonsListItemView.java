package com.hogwarts_eng_school.hogwarts_teacher.view.list;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.cabinet.Cabinet;
import com.hogwarts_eng_school.hogwarts_teacher.data.group.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.student.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.student.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentAttendanceService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.view.common.LoadableImageView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EViewGroup(R.layout.view_list_item_my_lessons)
public class MyLessonsListItemView extends RelativeLayout {
    public MyLessonsListItemView(Context context) {
        super(context);
    }

    public MyLessonsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @ViewById(R.id.icon)
    LoadableImageView iconView;
    @ViewById(R.id.time)
    TextView timeView;
    @ViewById(R.id.cabinet)
    TextView cabinetView;

    @ViewById(R.id.student_1)
    ImageView studentView1;
    @ViewById(R.id.student_2)
    ImageView studentView2;
    @ViewById(R.id.student_3)
    ImageView studentView3;
    @ViewById(R.id.student_4)
    ImageView studentView4;
    @ViewById(R.id.student_5)
    ImageView studentView5;
    @ViewById(R.id.student_6)
    ImageView studentView6;

    @Bean
    AuthService authService;
    @Bean
    TeachersService teachersService;
    @Bean
    StudentAttendanceService studentAttendanceService;

    public void bind(Cabinet cabinet, Lesson lesson, List<Student> students) {
        String login = authService.getUserInfo().orElseThrow(RuntimeException::new).getLogin();

        iconView.configure(
                lesson.getTeacherId() + "_" + lesson.getStartTime() + "_" + lesson.getFinishTime(),
                R.color.gray_very_light,
                () -> teachersService.getImage(login).orElseThrow(RuntimeException::new),
                () -> null
        );

        cabinetView.setText(cabinet.getName());

        timeView.setText(getResources().getString(
                R.string.lesson_time,
                getResources().getString(lesson.getStartTime().getTranslationId()),
                getResources().getString(lesson.getFinishTime().getTranslationId())
        ));

        setStudentStatus(studentView1, students.size() >= 1 ? students.get(0) : null);
        setStudentStatus(studentView2, students.size() >= 2 ? students.get(1) : null);
        setStudentStatus(studentView3, students.size() >= 3 ? students.get(2) : null);
        setStudentStatus(studentView4, students.size() >= 4 ? students.get(3) : null);
        setStudentStatus(studentView5, students.size() >= 5 ? students.get(4) : null);
        setStudentStatus(studentView6, students.size() >= 6 ? students.get(5) : null);
    }

    private void setStudentStatus(ImageView studentView, Student student) {
        studentView.setImageDrawable(getResources().getDrawable(student != null ? R.drawable.student : R.drawable.no_student));

        int color;

        if (student == null) {
            color = getResources().getColor(R.color.gray_dark);
        } else {
            Optional<StudentAttendance> studentAttendance = studentAttendanceService.getAttendance(student.getId());

            if (studentAttendance.isPresent()) {
                switch (studentAttendance.get().getType()) {
                    case VISITED:
                        color = getResources().getColor(R.color.success);
                        break;
                    case VALID_SKIP:
                        color = getResources().getColor(R.color.warning);
                        break;
                    case INVALID_SKIP:
                        color = getResources().getColor(R.color.danger);
                        break;
                    default:
                        throw new RuntimeException();
                }
            } else {
                color = getResources().getColor(R.color.gray_medium);
            }
        }

        studentView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
