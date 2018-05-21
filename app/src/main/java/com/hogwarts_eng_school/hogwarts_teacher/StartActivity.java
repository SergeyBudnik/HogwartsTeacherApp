package com.hogwarts_eng_school.hogwarts_teacher;

import android.view.View;
import android.view.animation.Animation;

import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

@EActivity(R.layout.activity_start)
public class StartActivity extends BaseActivity {
    @Bean
    AuthService authService;

    @ViewById(R.id.loading_spinner)
    View loadingSpinnerView;

    @AnimationRes(R.anim.spinner)
    Animation spinnerAnimation;

    @AfterViews
    void init() {
        loadingSpinnerView.setAnimation(spinnerAnimation);

        boolean authenticated = authService.getUserInfo().isPresent();

        redirect(
                authenticated ? LoadingActivity_.class : LoginActivity_.class,
                0, 0, true
        );
    }
}
