package com.hogwarts_eng_school.hogwarts_teacher;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.adapter.TodayLessonsListAdapter;
import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;
import com.hogwarts_eng_school.hogwarts_teacher.data.DayOfWeek;
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

@EActivity(R.layout.activity_timetable)
public class TimetableActivity extends BaseActivity {
    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.current_date)
    TextView currentDateView;
    @ViewById(R.id.cabinet)
    TextView cabinetView;
    @ViewById(R.id.lessons)
    ListView lessonsView;

    @Bean
    TodayLessonsListAdapter timetableListAdapter;

    @Bean
    LessonsService lessonsService;
    @Bean
    CabinetsService cabinetsService;
    @Bean
    TimeService timeService;

    private List<Cabinet> cabinets = new ArrayList<>();
    private int currentCabinetIndex = 0;

    @AfterViews
    void init() {
        menuView.setCurrentPage(MenuView.Page.TODAY_LESSONS);

        cabinets = Stream.of(cabinetsService.getCabinets())
                .filter(it -> !lessonsService.getTodayLessons(it).isEmpty())
                .toList();

        lessonsView.setAdapter(timetableListAdapter);

        DayOfWeek currentDay = timeService.getCurrentDay().orElseThrow(RuntimeException::new);

        currentDateView.setText(getResources().getString(R.string.current_day,
                getResources().getString(currentDay.getShortNameId()),
                timeService.getDate(),
                timeService.getMonth() + 1,
                timeService.getYear()
        ));

        fillPage();
    }

    @Click(R.id.menu_button)
    void onMenuClick() {
        rootView.openDrawer(Gravity.START);
    }

    @Click(R.id.previous_cabinet)
    void onPreviousCabinetClick() {
        currentCabinetIndex = currentCabinetIndex == 0 ? cabinets.size() - 1 : currentCabinetIndex - 1;

        fillPage();
    }

    @Click(R.id.next_cabinet)
    void onNextCabinetClick() {
        currentCabinetIndex = currentCabinetIndex == cabinets.size() - 1 ? 0 : currentCabinetIndex + 1;

        fillPage();
    }

    @ItemClick(R.id.lessons)
    void onLessonClick(TodayLessonsListAdapter.Item item) {
        Map<String, Serializable> extras = new HashMap<>();
        {
            extras.put(GroupActivity.EXTRA_GROUP_ID, item.getGroup().getId());
            extras.put(GroupActivity.EXTRA_LESSON_ID, item.getLesson().getId());
        }

        redirect(GroupActivity_.class, 0, 0, false, extras);
    }

    private void fillPage() {
        cabinetView.setText(cabinets.get(currentCabinetIndex).getName());

        List<TodayLessonsListAdapter.Item> items = new ArrayList<>();

        for (Lesson lesson : lessonsService.getTodayLessons(cabinetsService.getCabinets().get(currentCabinetIndex))) {
            items.add(TodayLessonsListAdapter.Item
                    .builder()
                    .group(lessonsService.getGroup(lesson.getId()).orElseThrow(RuntimeException::new))
                    .lesson(lesson)
                    .students(lessonsService.getStudents(lesson.getId()).orElseThrow(RuntimeException::new))
                    .build()
            );
        }

        timetableListAdapter.setItems(items);
        timetableListAdapter.notifyDataSetChanged();
    }
}
