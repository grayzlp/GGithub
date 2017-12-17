package com.grayzlp.ggithub.core.module.gist;

import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.gist.Gist;

import java.util.List;

public class GistContract {

    interface View extends BaseView<Presenter> {

        void showGists(List<Gist> gists);

        void showLoadingIndicator(boolean active);

        void showLoadingError();

        void showNoData();
    }


    interface Presenter extends BasePresenter<View> {

        void loadGists(boolean forceUpdate);

    }
}
