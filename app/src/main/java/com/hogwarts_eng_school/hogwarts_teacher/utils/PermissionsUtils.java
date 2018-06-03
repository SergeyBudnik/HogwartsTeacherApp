package com.hogwarts_eng_school.hogwarts_teacher.utils;

import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.hogwarts_eng_school.hogwarts_teacher.BaseActivity;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class PermissionsUtils {
    public static void doPermittedAction(Context context, Runnable action) {
        boolean hasPermission = checkSelfPermission(context, CALL_PHONE) == PERMISSION_GRANTED;

        if (hasPermission) {
            action.run();
        } else {
            ActivityCompat.requestPermissions(
                    (BaseActivity) context,
                    new String [] {CALL_PHONE},
                    1
            );
        }
    }
}
