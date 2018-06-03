package com.hogwarts_eng_school.hogwarts_teacher.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hogwarts_eng_school.hogwarts_teacher.data.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.view.list.TeacherListItemView;
import com.hogwarts_eng_school.hogwarts_teacher.view.list.TeacherListItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

@EBean
public class TeachersListAdapter extends BaseAdapter {
    @RootContext
    Context context;

    @Setter
    private List<Teacher> teachers = new ArrayList<>();

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Teacher getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public TeacherListItemView getView(int position, View convertView, ViewGroup viewGroup) {
        TeacherListItemView view = convertView == null ?
                TeacherListItemView_.build(context) :
                (TeacherListItemView) convertView;

        view.bind(getItem(position));

        return view;
    }
}
