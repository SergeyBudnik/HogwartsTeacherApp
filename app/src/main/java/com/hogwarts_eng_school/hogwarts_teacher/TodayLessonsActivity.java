package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.hogwarts_eng_school.hogwarts_teacher.adapter.TodayLessonsListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.group.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper.SchoolDataRestWrapper;
import com.hogwarts_eng_school.hogwarts_teacher.service.CabinetsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.LessonsService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TimeService;
import com.hogwarts_eng_school.hogwarts_teacher.view.app.MenuView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hogwarts_eng_school.hogwarts_teacher.LessonActivity.VIEW_LESSON_ACTION_ID;

@EActivity(R.layout.activity_today_lessons)
public class TodayLessonsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.content)
    View contentView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.lessons_refresh)
    SwipeRefreshLayout lessonsRefreshView;
    @ViewById(R.id.lessons)
    ListView lessonsView;

    @Bean
    TodayLessonsListAdapter todayLessonsListAdapter;

    @Bean
    LessonsService lessonsService;
    @Bean
    CabinetsService cabinetsService;
    @Bean
    TimeService timeService;

    @Bean
    SchoolDataRestWrapper schoolDataRestWrapper;

    @AfterViews
    void init() {
        initMenu(rootView, contentView);

        menuView.setCurrentPage(MenuView.Page.TODAY_LESSONS);

        lessonsView.setAdapter(todayLessonsListAdapter);

        lessonsRefreshView.setOnRefreshListener(this);

        setLessons();
    }

    @Override
    public void onRefresh() {
        schoolDataRestWrapper.load(new RestListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                onRefreshFinished();
            }

            @Override
            public void onFailure() {
                onRefreshFinished();
            }
        });
    }

    @UiThread
    void onRefreshFinished() {
        lessonsRefreshView.setRefreshing(false);

        setLessons();
    }

    @Click(R.id.menu_button)
    void onMenuClick() {
        rootView.openDrawer(Gravity.START);
    }

    @ItemClick(R.id.lessons)
    void onLessonClick(TodayLessonsListAdapter.Item item) {
        Map<String, Serializable> extras = new HashMap<>();
        {
            extras.put(LessonActivity.EXTRA_GROUP_ID, item.getGroup().getId());
            extras.put(LessonActivity.EXTRA_LESSON_ID, item.getLesson().getId());
        }

        redirectForResult(
                LessonActivity_.class,
                R.anim.enter_open_anim,
                R.anim.exit_open_anim,
                VIEW_LESSON_ACTION_ID,
                extras
        );
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
        List<TodayLessonsListAdapter.Item> items = new ArrayList<>();

        for (Lesson lesson : lessonsService.getTodayLessons()) {
            items.add(TodayLessonsListAdapter.Item
                    .builder()
                    .group(lessonsService.getGroup(lesson.getId()).orElseThrow(RuntimeException::new))
                    .cabinet(lessonsService.getCabinet(lesson.getId()).orElseThrow(RuntimeException::new))
                    .lesson(lesson)
                    .students(lessonsService.getStudents(lesson.getId()).orElseThrow(RuntimeException::new))
                    .build()
            );
        }

        todayLessonsListAdapter.setItems(items);
        todayLessonsListAdapter.notifyDataSetChanged();
    }
}
