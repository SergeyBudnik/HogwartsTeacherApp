package com.hogwarts_eng_school.hogwarts_teacher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import org.androidannotations.annotations.EActivity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@EActivity
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fabric.with(this, new Crashlytics());
    }

    public void initMenu(DrawerLayout rootView, View contentView) {
        rootView.setScrimColor(Color.TRANSPARENT);
        rootView.addDrawerListener(new ActionBarDrawerToggle(this, rootView, 0, 0) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                contentView.setTranslationX(drawerView.getWidth() * slideOffset);
            }
        });
    }

    public void redirect(Class<? extends BaseActivity> redirectTo, int enterAnim, int exitAnim, boolean doFinish) {
        redirect(redirectTo, enterAnim, exitAnim, doFinish, Collections.emptyMap());
    }

    public void redirect(Class<? extends BaseActivity> redirectTo,
                            int enterAnim,
                            int exitAnim,
                            boolean doFinish,
                            Map<String, Serializable> extras) {

        Intent i = new Intent(this, redirectTo);

        for (String extraKey : extras.keySet()) {
            i.putExtra(extraKey, extras.get(extraKey));
        }

        startActivity(i);
        overridePendingTransition(enterAnim, exitAnim);

        if (doFinish) {
            finish();
        }
    }

    public void redirectForResult(Class<? extends BaseActivity> redirectTo, int enterAnim, int exitAnim, int code) {
        redirectForResult(redirectTo, enterAnim, exitAnim, code, Collections.emptyMap());
    }

    public void redirectForResult(
            Class<? extends BaseActivity> redirectTo,
            int enterAnim,
            int exitAnim,
            int code,
            Map<String, Serializable> extras) {

        Intent i = new Intent(this, redirectTo);

        for (String extraKey : extras.keySet()) {
            i.putExtra(extraKey, extras.get(extraKey));
        }

        startActivityForResult(i, code);
        overridePendingTransition(enterAnim, exitAnim);
    }

    public void finishRedirect(int enterAnim, int exitAnim) {
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    public void finishRedirectForResult(int enterAnim, int exitAnim, int result) {
        finishRedirectForResult(enterAnim, exitAnim, result, Collections.emptyMap());
    }

    public void finishRedirectForResult(int enterAnim, int exitAnim, int result, Map<String, Serializable> extras) {
        Intent i = new Intent();

        for (String extraKey : extras.keySet()) {
            i.putExtra(extraKey, extras.get(extraKey));
        }

        setResult(result, i);

        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }
}
