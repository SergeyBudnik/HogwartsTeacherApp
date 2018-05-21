package com.hogwarts_eng_school.hogwarts_teacher.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.annimon.stream.function.Consumer;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.view.StudentsListItemView;
import com.hogwarts_eng_school.hogwarts_teacher.view.StudentsListItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

@EBean
public class StudentsListAdapter extends BaseAdapter {
    @RootContext
    Context context;

    @Setter private List<Student> items = new ArrayList<>();
    @Setter private Consumer<Student> onStudentAttendanceClick = it -> {};
    @Setter private Consumer<Student> onStudentPaymentClick = it -> {};

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Student getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        StudentsListItemView v = convertView == null ?
                StudentsListItemView_.build(context) :
                (StudentsListItemView) convertView;

        v.bind(getItem(position), onStudentAttendanceClick, onStudentPaymentClick);

        return v;
    }
}
