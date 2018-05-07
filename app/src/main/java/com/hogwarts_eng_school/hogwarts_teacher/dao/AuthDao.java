package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.AppUserInfo;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.Serializable;

import lombok.Data;

@Data
class AppUserInfoModel implements Serializable {
    private AppUserInfo appUserInfo;
}

@EBean(scope = EBean.Scope.Singleton)
public class AuthDao extends CommonDao<AppUserInfoModel> {
    @RootContext
    Context context;

    public AppUserInfo getUserInfo() {
        readCache(context, getFileName(), false);

        return getValue().getAppUserInfo();
    }

    public void setUserInfo(AppUserInfo appUserInfo) {
        readCache(context, getFileName(), false);

        getValue().setAppUserInfo(appUserInfo);

        persist(context);
    }

    @Override
    protected AppUserInfoModel newInstance() {
        return new AppUserInfoModel();
    }

    @Override
    protected String getFileName() {
        return AuthDao.class.getCanonicalName();
    }
}
