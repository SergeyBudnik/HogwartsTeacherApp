package com.hogwarts_eng_school.hogwarts_teacher;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.Student;
import com.hogwarts_eng_school.hogwarts_teacher.data.StudentPayment;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper.StudentPaymentRestWrapper;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentPaymentService;
import com.hogwarts_eng_school.hogwarts_teacher.service.StudentsService;
import com.hogwarts_eng_school.hogwarts_teacher.utils.DateUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_student_payment)
public class AddStudentPaymentActivity extends BaseActivity {
    public static final int ADD_PAYMENT_ACTION_ID = 2;

    public static final String EXTRA_STUDENT_ID = "studentId";

    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.content)
    View contentView;

    @ViewById(R.id.name)
    TextView nameView;
    @ViewById(R.id.month_payment_label)
    TextView monthPaymentLabelView;
    @ViewById(R.id.month_payment)
    TextView monthPaymentView;
    @ViewById(R.id.payment_amount_new)
    EditText paymentAmountNewView;

    @Bean
    StudentsService studentsService;
    @Bean
    StudentPaymentService studentPaymentService;

    @Bean
    StudentPaymentRestWrapper studentPaymentRestWrapper;

    @Extra(EXTRA_STUDENT_ID)
    long studentId;

    @AfterViews
    void init() {
        initMenu(rootView, contentView);

        Student student = studentsService.getStudent(studentId).orElseThrow(RuntimeException::new);

        nameView.setText(student.getName());

        monthPaymentLabelView.setText(getResources().getString(
                R.string.month_payment_label,
                getResources().getString(DateUtils.getCurrentMonthString())
        ));

        monthPaymentView.setText(getResources().getString(
                R.string.month_payment_amount,
                getMonthPayments()
        ));
    }

    @Click(R.id.payment_amount_new_add)
    void addPaymentAmountNewAdd() {
        long amount = Long.parseLong(paymentAmountNewView.getText().toString());

        studentPaymentRestWrapper.addPayment(
                studentId,
                amount,
                new RestListener<Void>() {
                    @Override
                    public void onSuccess(Void o) {
                        onClose(RESULT_OK);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        onClose(RESULT_CANCELED);
    }

    @Click(R.id.back)
    void onBackClick() {
        onClose(RESULT_CANCELED);
    }

    @UiThread
    void onClose(int result) {
        finishRedirectForResult(
                R.anim.enter_close_anim,
                R.anim.exit_close_anim,
                result
        );
    }

    private long getMonthPayments() {
        return Stream.of(studentPaymentService.getStudentPayments(studentId))
                .filter(it -> DateUtils.isCurrentMonth(it.getTime()))
                .map(StudentPayment::getAmount)
                .reduce((p1, p2) -> p1 + p2)
                .orElse(0L);
    }
}
