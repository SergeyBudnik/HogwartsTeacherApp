package com.hogwarts_eng_school.hogwarts_teacher.rest.listener;

public class RestListener<T> {
    public void onStart() {}
    public void onSuccess(T data) {}
    public void onFailure() {}

    public void onAuthFailure() {}
    public void onGeneralFailure() {}
}
