package com.grayzlp.ggithub.core.module.repo;


import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.repo.Repository;

import java.util.List;

public class RepoContract {

    public interface View extends BaseView<RepoContract.Presenter> {

        void showRepo(Repository repository);

        void showLoadError();

    }

    public interface Presenter extends BasePresenter<RepoContract.View> {

        void loadRepos(String repo, boolean forceUpdate);
    }
}
