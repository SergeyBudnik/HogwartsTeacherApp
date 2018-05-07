package com.hogwarts_eng_school.hogwarts_teacher.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;
import com.hogwarts_eng_school.hogwarts_teacher.data.Group;
import com.hogwarts_eng_school.hogwarts_teacher.data.Lesson;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.view.MyLessonsListItemView;
import com.hogwarts_eng_school.hogwarts_teacher.view.MyLessonsListItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Builder;

@EBean
public class MyLessonsListAdapter extends BaseAdapter {
    @Builder
    @Getter
    public static class Item {
        @NonNull private Cabinet cabinet;
        @NonNull private Group group;
        @NonNull private Lesson lesson;
        @NonNull private List<Student> students;
    }

    @RootContext
    Context context;

    @Setter
    private List<Item> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MyLessonsListItemView v = convertView == null ?
                MyLessonsListItemView_.build(context) :
                (MyLessonsListItemView) convertView;

        Item item = getItem(position);

        v.bind(item.getCabinet(), item.getLesson(), item.getStudents());

        return v;
    }
}
