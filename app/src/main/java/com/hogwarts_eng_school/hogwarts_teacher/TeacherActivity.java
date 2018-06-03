package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.data.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.view.LoadableImageView;
import com.hogwarts_eng_school.hogwarts_teacher.view.MenuView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Intent.ACTION_CALL;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

@EActivity(R.layout.activity_teacher)
public class TeacherActivity extends BaseActivity {
    public static final String EXTRA_TEACHER_ID = "teacherId";

    @Extra(EXTRA_TEACHER_ID)
    long teacherId;

    @ViewById(R.id.root)
    DrawerLayout rootView;
    @ViewById(R.id.content)
    View contentView;
    @ViewById(R.id.menu)
    MenuView menuView;
    @ViewById(R.id.teacher_icon)
    LoadableImageView teacherIconView;
    @ViewById(R.id.teacher)
    TextView teacherView;
    @ViewById(R.id.phone)
    TextView phoneView;

    @Bean
    TeachersService teachersService;

    private Teacher teacher;

    @AfterViews
    void init() {
        initMenu(rootView, contentView);

        menuView.setCurrentPage(MenuView.Page.NONE);

        teacher = teachersService.getTeacher(teacherId).orElseThrow(RuntimeException::new);

        teacherIconView.configure(
                "teacher",
                R.color.gray_very_light,
                () -> teachersService.getImage(teacher.getLogin()).orElse(null),
                () -> null
        );

        teacherView.setText(teacher.getName());
        phoneView.setText(teacher.getPhones().isEmpty() ? "" : teacher.getPhones().get(0));
    }

    @Click(R.id.back)
    void onBackClick() {
        onClose();
    }

    @Click(R.id.make_call)
    void onMakeCallClick() {
        if (!teacher.getPhones().isEmpty()) {
            boolean hasPermission = ContextCompat.checkSelfPermission(this, CALL_PHONE) == PERMISSION_GRANTED;

            if (hasPermission) {
                Intent intent = new Intent(ACTION_CALL, Uri.parse("tel:" + teacher.getPhones().get(0)));

                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this, new String [] {CALL_PHONE}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        onClose();
    }

    private void onClose() {
        finishRedirect(
                R.anim.enter_close_anim,
                R.anim.exit_close_anim
        );
    }
}
