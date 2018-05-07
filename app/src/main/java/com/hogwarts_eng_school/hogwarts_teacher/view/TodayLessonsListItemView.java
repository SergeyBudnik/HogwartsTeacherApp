package com.hogwarts_eng_school.hogwarts_teacher.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EViewGroup(R.layout.view_list_item_today_lessons)
public class TodayLessonsListItemView extends RelativeLayout {
    @ViewById(R.id.icon)
    LoadableImageView iconView;
    @ViewById(R.id.time)
    TextView timeView;

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

    @ViewById(R.id.cabinet_label)
    View cabinetLabelView;
    @ViewById(R.id.cabinet)
    TextView cabinetView;

    @Bean
    AuthService authService;
    @Bean
    TeachersService teachersService;

    public TodayLessonsListItemView(Context context) {
        super(context);
    }

    public TodayLessonsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Cabinet cabinet, Lesson lesson, List<Student> students, boolean newCabinet) {
        cabinetLabelView.setVisibility(newCabinet ? VISIBLE : GONE);
        cabinetView.setText(cabinet.getName());

        Teacher teacher = teachersService.getTeacher(lesson.getTeacherId()).orElseThrow(RuntimeException::new);

        iconView.configure(
                lesson.getTeacherId() + "_" + lesson.getStartTime() + "_" + lesson.getFinishTime(),
                R.color.gray_very_light,
                () -> teachersService.getImage(teacher.getLogin()).orElseThrow(RuntimeException::new),
                () -> null
        );

        timeView.setText(getResources().getString(
                R.string.lesson_time,
                getResources().getString(lesson.getStartTime().getTranslationId()),
                getResources().getString(lesson.getFinishTime().getTranslationId())
        ));

        setStudentStatus(studentView1, students.size() >= 1);
        setStudentStatus(studentView2, students.size() >= 2);
        setStudentStatus(studentView3, students.size() >= 3);
        setStudentStatus(studentView4, students.size() >= 4);
        setStudentStatus(studentView5, students.size() >= 5);
        setStudentStatus(studentView6, students.size() >= 6);
    }

    private void setStudentStatus(ImageView studentView, boolean exists) {
        studentView.setImageDrawable(getResources().getDrawable(exists ? R.drawable.student : R.drawable.no_student));

        studentView.setColorFilter(
                getResources().getColor(R.color.gray_dark),
                PorterDuff.Mode.SRC_IN
        );
    }
}
