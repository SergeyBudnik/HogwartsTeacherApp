package com.hogwarts_eng_school.hogwarts_teacher.rest.wrapper;

import com.hogwarts_eng_school.hogwarts_teacher.data.auth.AppUserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.data.auth.AuthInfo;
import com.hogwarts_eng_school.hogwarts_teacher.data.auth.LoginCredentials;
import com.hogwarts_eng_school.hogwarts_teacher.data.teacher.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.data.auth.UserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.rest.listener.RestListener;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.LoginRest;
import com.hogwarts_eng_school.hogwarts_teacher.rest.raw.TeacherRest;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@EBean
public class LoginRestWrapper extends AbstractAuthWrapper {
    @RestService
    LoginRest loginRest;
    @RestService
    TeacherRest teacherRest;

    @Bean
    AuthService authService;

    @Background
    public void login(String login, String password, RestListener<AuthInfo> listener) {
        try {
            listener.onStart();

            AuthInfo authInfo = loginRest.login(
                    LoginCredentials
                            .builder()
                            .login(login)
                            .password(password)
                            .build()
            );

            UserInfo userInfo = loginRest.getUserInfo(authInfo.getToken());

            authenticateTo(authInfo.getToken(), teacherRest);

            Teacher teacher = teacherRest.getTeacher(userInfo.getLogin());

            authService.setUserInfo(AppUserInfo
                    .builder()
                    .id(teacher.getId())
                    .name(teacher.getName())
                    .login(userInfo.getLogin())
                    .authToken(authInfo.getToken())
                    .build()
            );

            listener.onSuccess(authInfo);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                listener.onAuthFailure();
            } else {
                listener.onGeneralFailure();
            }

            listener.onFailure();
        } catch (Exception e) {
            listener.onGeneralFailure();
            listener.onFailure();
        }
    }
}

