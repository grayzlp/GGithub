package com.grayzlp.ggithub.data.repo.star;


import com.grayzlp.ggithub.data.model.repo.Starred;

import java.util.List;

import io.reactivex.Flowable;

public interface StarsDataSource {

    Flowable<List<Starred>> getStarreds();

    void starRepo(String owner, String repo);

    void unstarRepo(String owner, String repo);

    void refresh();

}
