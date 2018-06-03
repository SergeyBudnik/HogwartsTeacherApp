package com.hogwarts_eng_school.hogwarts_teacher.view.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentAttendanceService;
import com.hogwarts_eng_school.hogwarts_teacher.utils.PermissionsUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import static android.content.Intent.ACTION_CALL;

@EViewGroup(R.layout.view_list_item_students)
public class StudentsListItemView extends RelativeLayout {
    @ViewById(R.id.name)
    TextView nameView;
    @ViewById(R.id.call)
    ImageView callView;
    @ViewById(R.id.attendance)
    ImageView attendanceView;

    @Bean
    StudentAttendanceService studentAttendanceService;

    private Student student;
    private Consumer<Student> onStudentAttendanceClick;
    private Consumer<Student> onStudentPaymentClick;

    public StudentsListItemView(Context context) {
        super(context);
    }

    public StudentsListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(
            Student student,
            Consumer<Student> onStudentAttendanceClick,
            Consumer<Student> onStudentPaymentClick
    ) {
        this.student = student;
        this.onStudentAttendanceClick = onStudentAttendanceClick;
        this.onStudentPaymentClick = onStudentPaymentClick;

        nameView.setText(student.getName());

        callView.getDrawable().setColorFilter(
                getResources().getColor(student.getPhones().isEmpty() ? R.color.gray_light : R.color.green),
                PorterDuff.Mode.SRC_IN
        );

        setAttendance(student);
    }

    private void setAttendance(Student student) {
        Optional<StudentAttendance> studentAttendance = studentAttendanceService.getAttendance(student.getId());

        int color;

        if (studentAttendance.isPresent()) {
            switch (studentAttendance.get().getType()) {
                case VISITED:
                    color = getResources().getColor(R.color.success);
                    break;
                case VALID_SKIP:
                    color = getResources().getColor(R.color.warning);
                    break;
                case INVALID_SKIP:
                    color = getResources().getColor(R.color.danger);
                    break;
                default:
                    throw new RuntimeException();
            }
        } else {
            color = getResources().getColor(R.color.gray_medium);
        }

        attendanceView.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @SuppressLint("MissingPermission")
    @Click(R.id.call)
    void onCallClick() {
        if (!student.getPhones().isEmpty()) {
            PermissionsUtils.doPermittedAction(
                    getContext(),
                    () -> {
                        Intent intent = new Intent(ACTION_CALL, Uri.parse("tel:" + student.getPhones().get(0)));

                        getContext().startActivity(intent);
                    }
            );
        }
    }

    @Click(R.id.attendance)
    void onAttendanceClick() {
        onStudentAttendanceClick.accept(student);
    }

    @Click(R.id.payment)
    void onPaymentClick() {
        onStudentPaymentClick.accept(student);
    }
}
