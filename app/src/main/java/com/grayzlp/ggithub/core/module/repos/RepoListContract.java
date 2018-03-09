package com.grayzlp.ggithub.core.module.repos;


import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.repo.Repository;

import java.util.List;

public class RepoListContract {

    public interface View extends BaseView<RepoListContract.Presenter> {

        void showRepos(List<Repository> user);

        void showLoadError();

    }

    public interface Presenter extends BasePresenter<RepoListContract.View> {

        void loadUserRepos(String username, boolean forceUpdate);
    }
}
