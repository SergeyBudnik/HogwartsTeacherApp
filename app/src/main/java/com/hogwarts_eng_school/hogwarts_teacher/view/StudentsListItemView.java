package com.hogwarts_eng_school.hogwarts_teacher.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.function.Consumer;
import com.hogwarts_eng_school.hogwarts_teacher.BaseActivity;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Intent.ACTION_CALL;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

@EViewGroup(R.layout.view_list_item_students)
public class StudentsListItemView extends RelativeLayout {
    @ViewById(R.id.name)
    TextView nameView;
    @ViewById(R.id.call)
    ImageView callView;

    private Student student;
    private Consumer<Long> onStudentAttendanceClick;

    public StudentsListItemView(Context context) {
        super(context);
    }

    public StudentsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Student student, Consumer<Long> onStudentAttendanceClick) {
        this.student = student;
        this.onStudentAttendanceClick = onStudentAttendanceClick;

        nameView.setText(student.getName());

        callView.getDrawable().setColorFilter(
                getResources().getColor(student.getPhones().isEmpty() ? R.color.gray_light : R.color.green),
                PorterDuff.Mode.SRC_IN
        );
    }

    @Click(R.id.call)
    void onCallClick() {
        if (!student.getPhones().isEmpty()) {
            boolean hasPermission = checkSelfPermission(getContext(), CALL_PHONE) == PERMISSION_GRANTED;

            if (hasPermission) {
                Intent intent = new Intent(ACTION_CALL, Uri.parse("tel:" + student.getPhones().get(0)));

                getContext().startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(
                        (BaseActivity) getContext(),
                        new String [] {CALL_PHONE},
                        1
                );
            }
        }
    }

    @Click(R.id.attendance)
    void onAttendanceClick() {
        onStudentAttendanceClick.accept(student.getId());
    }
}
