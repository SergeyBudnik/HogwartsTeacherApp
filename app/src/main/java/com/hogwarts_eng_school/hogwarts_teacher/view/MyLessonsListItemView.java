package com.hogwarts_eng_school.hogwarts_teacher.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.utils.BitmapUtils;

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
