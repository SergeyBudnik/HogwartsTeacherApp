package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.dao.CabinetsDao;
import com.hogwarts_eng_school.hogwarts_teacher.data.cabinet.Cabinet;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class CabinetsService {
    @Bean
    CabinetsDao cabinetsDao;

    public Optional<Cabinet> getCabinet(long id) {
        return Stream.of(cabinetsDao.getCabinets())
                .filter(it -> Objects.equals(id, it.getId()))
                .findFirst();
    }

    public void setCabinets(List<Cabinet> cabinets) {
        cabinetsDao.setCabinets(cabinets);
    }
}
