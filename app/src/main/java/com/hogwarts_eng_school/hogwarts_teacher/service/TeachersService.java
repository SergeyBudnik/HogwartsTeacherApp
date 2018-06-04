package com.hogwarts_eng_school.hogwarts_teacher.service;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.R;
import com.hogwarts_eng_school.hogwarts_teacher.dao.TeachersDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.teacher.Teacher;
import com.hogwarts_eng_school.hogwarts_teacher.utils.BitmapUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class TeachersService {
    @RootContext
    Context context;

    @Bean
    TeachersDao teachersDao;

    private byte [] ekaterinaKryukovaIcon = null;
    private byte [] ekaterinaUdodovaIcon = null;
    private byte [] nadezhdaKropinaIcon = null;

    public List<Teacher> getTeachers() {
        return teachersDao.getTeachers();
    }

    public Optional<Teacher> getTeacher(long id) {
        return Stream.of(teachersDao.getTeachers())
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();
    }

    public void setTeachers(List<Teacher> teachers) {
        teachersDao.setTeachers(teachers);
    }

    public Optional<byte []> getImage(String login) {
        switch (login) {
            case "ekaterina-kryukova":
                return ekaterinaKryukovaIcon == null ? getTeacherIcon(login) : Optional.of(ekaterinaKryukovaIcon);
            case "ekaterina-udodova":
                return ekaterinaUdodovaIcon == null ? getTeacherIcon(login) : Optional.of(ekaterinaUdodovaIcon);
            case "nadezhda-kropina":
                return nadezhdaKropinaIcon == null ? getTeacherIcon(login) : Optional.of(nadezhdaKropinaIcon);
        }

        return Optional.empty();
    }

    private Optional<byte []> getTeacherIcon(String login) {
        byte [] icon = null;

        switch (login) {
            case "ekaterina-kryukova":
                icon = getIcon(R.drawable.ekaterina_kryukova);
                ekaterinaKryukovaIcon = icon;
                break;
            case "ekaterina-udodova":
                icon = getIcon(R.drawable.ekaterina_udodova);
                ekaterinaUdodovaIcon = icon;
                break;
            case "nadezhda-kropina":
                icon = getIcon(R.drawable.nadezhda_kropina);
                nadezhdaKropinaIcon = icon;
                break;
        }

        return Optional.ofNullable(icon);
    }

    private byte [] getIcon(int id) {
        Drawable drawable = context.getResources().getDrawable(id);

        return BitmapUtils.iconToBytes(((BitmapDrawable) drawable).getBitmap());
    }
}
