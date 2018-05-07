package com.hogwarts_eng_school.hogwarts_teacher;

import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_start)
public class StartActivity extends BaseActivity {
    @Bean
    AuthService authService;

    @AfterViews
    void init() {
        boolean authenticated = authService.getUserInfo().isPresent();

        redirect(
                authenticated ? LoadingActivity_.class : LoginActivity_.class,
                0, 0, true
        );
    }
}
