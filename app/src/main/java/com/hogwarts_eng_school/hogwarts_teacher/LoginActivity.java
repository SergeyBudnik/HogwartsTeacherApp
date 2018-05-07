package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hogwarts_eng_school.hogwarts_teacher.data.AuthInfo;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper.LoginRestWrapper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewById(R.id.login)
    EditText loginView;
    @ViewById(R.id.password)
    EditText passwordView;

    @ViewById(R.id.do_login)
    View doLoginView;
    @ViewById(R.id.do_login_spinner)
    View doLoginSpinnerView;

    @ViewById(R.id.error_auth)
    View errorAuthView;
    @ViewById(R.id.error_network)
    View errorNetworkView;

    @Bean
    LoginRestWrapper loginRestWrapper;

    @AnimationRes(R.anim.spinner)
    Animation spinnerAnim;

    @Click(R.id.do_login)
    void onDoLoginClick() {
        hideKeyboard();

        String login = loginView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();

        loginRestWrapper.login(login, password, new RestListener<AuthInfo>() {
            @Override
            public void onStart() {
                onLoginStart();
            }

            @Override
            public void onSuccess(AuthInfo data) {
                onLoginSuccess();
            }

            @Override
            public void onAuthFailure() {
                onLoginAuthFailure();
            }

            @Override
            public void onGeneralFailure() {
                onLoginNetworkFailure();
            }
        });
    }

    @UiThread
    void onLoginStart() {
        errorAuthView.setVisibility(GONE);
        errorNetworkView.setVisibility(GONE);

        doLoginView.setVisibility(GONE);

        doLoginSpinnerView.setAnimation(spinnerAnim);
        doLoginSpinnerView.setVisibility(VISIBLE);
    }

    @UiThread
    void onLoginSuccess() {
        redirect(LoadingActivity_.class, 0, 0, true);
    }

    @UiThread
    void onLoginAuthFailure() {
        errorAuthView.setVisibility(VISIBLE);

        doLoginView.setVisibility(VISIBLE);

        doLoginSpinnerView.clearAnimation();
        doLoginSpinnerView.setVisibility(GONE);
    }

    @UiThread
    void onLoginNetworkFailure() {
        errorNetworkView.setVisibility(VISIBLE);

        doLoginView.setVisibility(VISIBLE);

        doLoginSpinnerView.clearAnimation();
        doLoginSpinnerView.setVisibility(GONE);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
