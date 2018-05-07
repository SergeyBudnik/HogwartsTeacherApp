package com.hogwarts_eng_school.hogwarts_teacher.service;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hogwarts_eng_school.hogwarts_teacher.data.Cabinet;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean(scope = EBean.Scope.Singleton)
public class CabinetsService {
    private List<Cabinet> cabinets = new ArrayList<>();

    public List<Cabinet> getCabinets() {
        return cabinets;
    }

    public Optional<Cabinet> getCabinet(long id) {
        return Stream.of(cabinets)
                .filter(it -> Objects.equals(id, it.getId()))
                .findFirst();
    }

    public void setCabinets(List<Cabinet> cabinets) {
        this.cabinets = cabinets;
    }
}
