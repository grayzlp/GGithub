package com.grayzlp.ggithub.core.module.repos;


import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.repo.Repository;

import java.util.List;

public class RepoContract {

    public interface View extends BaseView<RepoContract.Presenter> {

        void showRepos(List<Repository> user);

        void showLoadError();

    }

    public interface Presenter extends BasePresenter<RepoContract.View> {

        void loadUserRepos(String username, boolean forceUpdate);
    }
}
