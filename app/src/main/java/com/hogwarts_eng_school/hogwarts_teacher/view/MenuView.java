package com.hogwarts_eng_school.hogwarts_teacher.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hogwarts_eng_school.hogwarts_teacher.BaseActivity;
import com.hogwarts_eng_school.hogwarts_teacher.LoginActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.MyLessonsActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.TodayLessonsActivity_;
import com.hogwarts_eng_school.hogwarts_teacher.data.AppUserInfo;
import com.hogwarts_eng_school.hogwarts_teacher.service.AuthService;
import com.hogwarts_eng_school.hogwarts_teacher.service.TeachersService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import lombok.Getter;

@EViewGroup(R.layout.view_menu)
public class MenuView extends RelativeLayout {
    public enum Page {
        NONE(0, 0),
        MY_LESSONS(R.id.my_lessons_icon, R.id.my_lessons_label),
        TODAY_LESSONS(R.id.today_lessons_icon, R.id.today_lessons_label);

        @Getter private int iconViewId;
        @Getter private int labelViewId;

        Page(int iconViewId, int labelViewId) {
            this.iconViewId = iconViewId;
            this.labelViewId = labelViewId;
        }
    }

    @ViewById(R.id.icon)
    LoadableImageView iconView;
    @ViewById(R.id.name)
    TextView nameView;
    @ViewById(R.id.login)
    TextView loginView;
    @ViewById(R.id.version)
    TextView versionView;

    @Bean
    AuthService authService;
    @Bean
    TeachersService teachersService;

    public MenuView(Context context) {
        super(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
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

        versionView.setText(getResources().getString(R.string.menu_version, getVersion()));
    }

    @Click(R.id.my_lessons)
    void onMyLessonsClick() {
        getActivity().redirect(MyLessonsActivity_.class, 0, 0, true);
    }

    @Click(R.id.today_lessons)
    void onTodayLessonsClick() {
        getActivity().redirect(TodayLessonsActivity_.class, 0, 0, true);
    }

    @Click(R.id.exit)
    void onExitClick() {
        authService.clearAuthInfo();

        getActivity().redirect(LoginActivity_.class, 0, 0, true);
    }

    public void setCurrentPage(Page currentPage) {
        for (Page page : Page.values()) {
            int color = getResources().getColor(page == currentPage ? R.color.primary_normal : R.color.black);

            ImageView iconView = findViewById(page.getIconViewId());

            if (iconView != null) {
                iconView.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }

            TextView labelView = findViewById(page.getLabelViewId());

            if (labelView != null) {
                labelView.setTextColor(color);
            }
        }
    }

    private String getVersion() {
        try {
            return getContext().getPackageManager().getPackageInfo(
                    getContext().getPackageName(),
                    0
            ).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private BaseActivity getActivity() {
        return (BaseActivity) getContext();
    }
}
