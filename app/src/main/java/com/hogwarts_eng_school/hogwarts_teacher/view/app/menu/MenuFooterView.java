package com.hogwarts_eng_school.hogwarts_teacher.view.app.menu;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.BaseActivity;
import com.hogwarts_eng_school.hogwarts_teacher.LoginActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_menu_footer)
public class MenuFooterView extends LinearLayout {
    @ViewById(R.id.version)
    TextView versionView;

    @Bean
    AuthService authService;

    public MenuFooterView(Context context) {
        super(context);
    }

    public MenuFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void init() {
        versionView.setText(getResources().getString(R.string.menu_version, getVersion()));
    }

    @Click(R.id.exit)
    void onExitClick() {
        authService.clearAuthInfo();

        ((BaseActivity) getContext()).redirect(LoginActivity_.class, 0, 0, true);
    }

    private String getVersion() {
        if (isInEditMode()) {
            return "1.0.0";
        }

        try {
            return getContext().getPackageManager().getPackageInfo(
                    getContext().getPackageName(),
                    0
            ).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
