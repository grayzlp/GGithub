package com.grayzlp.ggithub.data.repo.gist;


import com.grayzlp.ggithub.data.model.gist.Gist;

import java.util.List;

import io.reactivex.Flowable;

public interface GistsDataSource {

    Flowable<List<Gist>> getGists();

    void refresh();
}
