package com.hogwarts_eng_school.hogwarts_teacher;

import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper.StudentAttendanceRestWrapper;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentsService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance.Type.INVALID_SKIP;
import static com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance.Type.VALID_SKIP;
import static com.hogwarts_eng_school.hogwarts_teacher.data.StudentAttendance.Type.VISITED;

@EActivity(R.layout.activity_add_student_attendance)
public class AddStudentAttendanceActivity extends BaseActivity {
    public static final int ADD_ATTENDANCE_ACTION_ID = 1;

    private static final float ENABLED_ALPHA = 1.0f;
    private static final float DISABLED_ALPHA = 0.4f;

    public static final String EXTRA_STUDENT_ID = "studentId";

    @ViewById(R.id.student)
    TextView studentView;

    @ViewById(R.id.attended)
    View attendedView;
    @ViewById(R.id.valid_skip)
    View validSkipView;
    @ViewById(R.id.invalid_skip)
    View invalidSkipView;

    @ViewById(R.id.submit)
    View submitView;
    @ViewById(R.id.submit_spinner)
    View submitSpinnerView;

    @Bean
    StudentsService studentsService;
    @Bean
    StudentAttendanceRestWrapper studentAttendanceRestWrapper;

    @AnimationRes(R.anim.spinner)
    Animation spinnerAnimation;

    private StudentAttendance.Type type = null;

    @Extra(EXTRA_STUDENT_ID)
    public long studentId;

    @AfterViews
    void init() {
        Student student = Stream.of(studentsService.getStudents())
                .filter(it -> Objects.equals(it.getId(), studentId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        studentView.setText(student.getName());
    }

    @Click(R.id.attended)
    void onAttendedClick() {
        toggleType(VISITED);
    }

    @Click(R.id.valid_skip)
    void onValidSkipClick() {
        toggleType(VALID_SKIP);
    }

    @Click(R.id.invalid_skip)
    void onInvalidSkipClick() {
        toggleType(INVALID_SKIP);
    }

    @Click(R.id.submit)
    void onSubmit() {
        if (type != null) {
            submitView.setVisibility(GONE);

            submitSpinnerView.setVisibility(VISIBLE);
            submitSpinnerView.setAnimation(spinnerAnimation);

            studentAttendanceRestWrapper.addAttendance(studentId, type, new RestListener<Void>() {
                @Override
                public void onSuccess(Void data) {
                    onAttendanceAdded();
                }
            });
        }
    }

    @UiThread
    void onAttendanceAdded() {
        finishRedirectForResult(0, 0, RESULT_OK);
    }

    private void toggleType(StudentAttendance.Type type) {
        this.type = this.type == type ? null : type;

        submitView.setAlpha(this.type == null ? DISABLED_ALPHA : ENABLED_ALPHA);

        attendedView.setAlpha(this.type == VISITED ? ENABLED_ALPHA : DISABLED_ALPHA);
        validSkipView.setAlpha(this.type == VALID_SKIP ? ENABLED_ALPHA : DISABLED_ALPHA);
        invalidSkipView.setAlpha(this.type == INVALID_SKIP ? ENABLED_ALPHA : DISABLED_ALPHA);
    }
}
