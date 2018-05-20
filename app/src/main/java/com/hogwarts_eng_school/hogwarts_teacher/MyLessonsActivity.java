package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.ListView;

import com.hogwarts_eng_school.hogwarts_teacher.adapter.MyLessonsListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.service.CabinetsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.LessonsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TimeService;
import com.hogwarts_eng_school.hogwarts_teacher.view.MenuView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hogwarts_eng_school.hogwarts_teacher.LessonActivity.VIEW_LESSON_ACTION_ID;

@EActivity(R.layout.activity_my_lessons)
public class MyLessonsActivity extends BaseActivity {
    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.lessons)
    ListView lessons;

    @Bean
    LessonsService lessonsService;
    @Bean
    CabinetsService cabinetsService;
    @Bean
    TimeService timeService;

    @Bean
    MyLessonsListAdapter myLessonsListAdapter;

    @AfterViews
    void init() {
        rootView.setScrimColor(Color.TRANSPARENT);

        menuView.setCurrentPage(MenuView.Page.MY_LESSONS);

        setLessons();
    }

    @Click(R.id.menu_button)
    void onMenuClick() {
        rootView.openDrawer(Gravity.START);
    }

    @ItemClick(R.id.lessons)
    void onLessonClick(MyLessonsListAdapter.Item item) {
        Map<String, Serializable> extras = new HashMap<>();
        {
            extras.put(LessonActivity.EXTRA_GROUP_ID, item.getGroup().getId());
            extras.put(LessonActivity.EXTRA_LESSON_ID, item.getLesson().getId());
        }

        redirectForResult(LessonActivity_.class, 0, 0, VIEW_LESSON_ACTION_ID, extras);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == VIEW_LESSON_ACTION_ID) {
                setLessons();
            }
        }
    }

    private void setLessons() {
        List<MyLessonsListAdapter.Item> items = new ArrayList<>();

        for (Lesson lesson : lessonsService.getMyLessons()) {
            items.add(MyLessonsListAdapter.Item
                    .builder()
                    .group(lessonsService.getGroup(lesson.getId()).orElseThrow(RuntimeException::new))
                    .cabinet(lessonsService.getCabinet(lesson.getId()).orElseThrow(RuntimeException::new))
                    .lesson(lesson)
                    .students(lessonsService.getStudents(lesson.getId()).orElseThrow(RuntimeException::new))
                    .build()
            );
        }

        myLessonsListAdapter.setItems(items);

        lessons.setAdapter(myLessonsListAdapter);
    }
}
