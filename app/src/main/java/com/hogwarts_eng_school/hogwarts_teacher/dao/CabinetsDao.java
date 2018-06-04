package com.hogwarts_eng_school.hogwarts_teacher.dao;

import android.content.Context;

import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CabinetsModel {
    private List<Cabinet> cabinets = new ArrayList<>();
}

@EBean(scope = EBean.Scope.Singleton)
public class CabinetsDao extends CommonDao<CabinetsModel> {
    @RootContext
    Context context;

    public List<Cabinet> getCabinets() {
        readCache(context, getFileName(), false);

        return getValue().getCabinets();
    }

    public void setCabinets(List<Cabinet> cabinets) {
        readCache(context, getFileName(), false);

        getValue().setCabinets(cabinets);

        persist(context);
    }

    @Override
    protected CabinetsModel newInstance() {
        return new CabinetsModel();
    }

    @Override
    protected String getFileName() {
        return CabinetsDao.class.getCanonicalName();
    }
}
