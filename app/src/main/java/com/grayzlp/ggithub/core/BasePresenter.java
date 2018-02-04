package com.grayzlp.ggithub.core;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
