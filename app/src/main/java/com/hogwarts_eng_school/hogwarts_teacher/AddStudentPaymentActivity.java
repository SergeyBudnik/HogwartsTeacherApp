package com.hogwarts_eng_school.hogwarts_teacher;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_student_payment)
public class AddStudentPaymentActivity extends BaseActivity {
    public static final int ADD_PAYMENT_ACTION_ID = 2;

    public static final String EXTRA_STUDENT_ID = "studentId";

    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.content)
    View contentView;

    @AfterViews
    void init() {
        initMenu(rootView, contentView);
    }

    @Override
    public void onBackPressed() {
        onClose(RESULT_CANCELED);
    }

    @Click(R.id.back)
    void onBackClick() {
        onClose(RESULT_CANCELED);
    }

    private void onClose(int result) {
        finishRedirectForResult(
                R.anim.enter_close_anim,
                R.anim.exit_close_anim,
                result
        );
    }
}
