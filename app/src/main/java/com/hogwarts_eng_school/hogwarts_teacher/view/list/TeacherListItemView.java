package com.hogwarts_eng_school.hogwarts_teacher.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.view.LoadableImageView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_list_item_teacher)
public class TeacherListItemView extends RelativeLayout {
    @ViewById(R.id.icon)
    LoadableImageView iconView;
    @ViewById(R.id.name)
    TextView nameView;

    @Bean
    TeachersService teachersService;

    public TeacherListItemView(Context context) {
        super(context);
    }

    public TeacherListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Teacher teacher) {
        iconView.configure(
                "" + teacher.getId(),
                R.color.gray_very_light,
                () -> teachersService.getImage(teacher.getLogin()).orElse(null),
                () -> null
        );

        nameView.setText(teacher.getName());
    }
}
