package com.hogwarts_eng_school.hogwarts_teacher.view.app.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.data.auth.AppUserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;
import com.hogwarts_eng_school.hogwarts_teacher.view.common.LoadableImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_menu_header)
public class MenuHeaderView extends LinearLayout {
    @ViewById(R.id.icon)
    LoadableImageView iconView;
    @ViewById(R.id.name)
    TextView nameView;
    @ViewById(R.id.login)
    TextView loginView;

    @Bean
    AuthService authService;
    @Bean
    TeachersService teachersService;

    public MenuHeaderView(Context context) {
        super(context);
    }

    public MenuHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void init() {
        if (!isInEditMode()) {
            AppUserInfo appUserInfo = authService.getUserInfo().orElseThrow(RuntimeException::new);

            iconView.configure(
                    "user-icon",
                    R.color.gray_very_light,
                    () -> teachersService.getImage(appUserInfo.getLogin()).orElseThrow(RuntimeException::new),
                    () -> null
            );

            nameView.setText(appUserInfo.getName());
            loginView.setText(appUserInfo.getLogin());
        }
    }
}
