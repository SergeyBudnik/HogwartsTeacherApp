package com.hogwarts_eng_school.hogwarts_teacher.view.app.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.BaseActivity;
import com.hogwarts_eng_school.hogwarts_teacher.MyLessonsActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.TeachersListActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.TodayLessonsActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.view.app.menu.MenuItemView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import lombok.Getter;

@EViewGroup(R.layout.view_menu)
public class MenuView extends RelativeLayout {
    public enum Page {
        NONE(0),
        MY_LESSONS(R.id.my_lessons),
        TODAY_LESSONS(R.id.today_lessons),
        TEACHERS(R.id.teachers);

        @Getter private int id;

        Page(int id) { this.id = id; }
    }

    @ViewById(R.id.my_lessons)
    MenuItemView myLessonsMenuItemView;
    @ViewById(R.id.today_lessons)
    MenuItemView todayLessonsMenuItemView;
    @ViewById(R.id.teachers)
    MenuItemView teachersMenuItemView;

    public MenuView(Context context) {
        super(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void init() {
        BaseActivity activity = (BaseActivity) getContext();

        myLessonsMenuItemView.bind(R.drawable.my_lessons, R.string.menu_my_lessons, () ->
            activity.redirect(MyLessonsActivity_.class, 0, 0, true)
        );

        todayLessonsMenuItemView.bind(R.drawable.today_lessons, R.string.menu_today_lessons, () ->
            activity.redirect(TodayLessonsActivity_.class, 0, 0, true)
        );

        teachersMenuItemView.bind(R.drawable.teacher, R.string.menu_teachers, () ->
            activity.redirect(TeachersListActivity_.class, 0, 0, true)
        );
    }

    public void setCurrentPage(Page currentPage) {
        Stream
                .of(Page.values())
                .filter(it -> it != Page.NONE)
                .forEach(page -> findViewById(page.id).setSelected(currentPage == page));
    }
}
