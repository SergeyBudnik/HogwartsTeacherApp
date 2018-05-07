package com.hogwarts_eng_school.hogwarts_teacher;

import android.view.View;
import android.view.animation.Animation;

import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper.SchoolDataRestWrapper;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

@EActivity(R.layout.activity_loading)
public class LoadingActivity extends BaseActivity {
    @ViewById(R.id.loading_container)
    View loadingContainer;
    @ViewById(R.id.loading_failed_container)
    View loadingFailedContainer;

    @ViewById(R.id.loading_spinner)
    View loadingSpinnerView;

    @Bean
    SchoolDataRestWrapper schoolDataRestWrapper;

    @Bean
    AuthService authService;

    @AnimationRes(R.anim.spinner)
    Animation spinnerAnimation;

    @AfterViews
    void init() {
        loadingSpinnerView.setAnimation(spinnerAnimation);

        startLoading();
    }

    @UiThread
    void onLoadingSuccess() {
        redirect(MyLessonsActivity_.class, 0, 0, true);
    }

    @UiThread
    void onLoadingFailure() {
        loadingSpinnerView.clearAnimation();

        loadingContainer.setVisibility(View.GONE);
        loadingFailedContainer.setVisibility(View.VISIBLE);
    }

    @UiThread
    void onLoadingAuthFailure() {
        authService.clearAuthInfo();

        redirect(LoginActivity_.class, 0, 0, true);
    }

    @Click(R.id.loading_failed_container)
    void onLoadingFailedContainerClick() {
        startLoading();
    }

    private void startLoading() {
        loadingSpinnerView.setAnimation(spinnerAnimation);

        loadingContainer.setVisibility(View.VISIBLE);
        loadingFailedContainer.setVisibility(View.GONE);

        schoolDataRestWrapper.load(new RestListener<Void>() {
            @Override
            public void onSuccess(Void o) {
                onLoadingSuccess();
            }

            @Override
            public void onAuthFailure() {
                onLoadingAuthFailure();
            }

            @Override
            public void onGeneralFailure() {
                onLoadingFailure();
            }
        });
    }
}
