package com.hogwarts_eng_school.hogwarts_teacher;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.hogwarts_eng_school.hogwarts_teacher.adapter.TeachersListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.teacher.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.view.app.menu.MenuView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;

@EActivity(R.layout.activity_teachers_list)
public class TeachersListActivity extends BaseActivity {
    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.content)
    View contentView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.teachers)
    ListView teachersView;

    @Bean
    TeachersListAdapter teachersListAdapter;

    @Bean
    TeachersService teachersService;

    @AfterViews
    void init() {
        initMenu(rootView, contentView);

        menuView.setCurrentPage(MenuView.Page.TEACHERS);

        setTeachers();
    }

    @Click(R.id.menu_button)
    void onMenuClick() {
        rootView.openDrawer(Gravity.START);
    }

    @ItemClick(R.id.teachers)
    void onTeacherClick(Teacher teacher) {
        redirect(
                TeacherActivity_.class,
                R.anim.enter_open_anim, R.anim.exit_open_anim,
                false,
                Collections.singletonMap(TeacherActivity.EXTRA_TEACHER_ID, teacher.getId())
        );
    }

    private void setTeachers() {
        teachersListAdapter.setTeachers(teachersService.getTeachers());
        teachersView.setAdapter(teachersListAdapter);
    }
}
