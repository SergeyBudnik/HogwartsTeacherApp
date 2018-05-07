package com.hogwarts_eng_school.hogwarts_teacher.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hogwarts_eng_school.hogwarts_teacher.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;

@EViewGroup(R.layout.view_popup_attendance)
public class StudentAttendancePopupView extends RelativeLayout {
    public StudentAttendancePopupView(Context context) {
        super(context);
    }

    public StudentAttendancePopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    @Click(R.id.root)
    void onRootClick() {
        hide();
    }

    @Click(R.id.attended)
    void onAttendedClick() {
        hide();
    }

    @Click(R.id.valid_skip)
    void onValidSkipClick() {
        hide();
    }

    @Click(R.id.invalid_skip)
    void onInvalidSkipClick() {
        hide();
    }
}
