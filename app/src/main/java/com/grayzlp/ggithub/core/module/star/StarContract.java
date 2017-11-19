package com.grayzlp.ggithub.core.module.star;


import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.repo.Starred;

import java.util.List;

public class StarContract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void showLoadingError();

        void showNoStar();

        void showStar(List<Starred> starreds);

        void showRepo(Starred starred);

    }


    interface Presenter extends BasePresenter<View> {
        @Override
        void takeView(View view);

        @Override
        void dropView();

        void loadStars(boolean forceUpdate);

        void unstar(Starred starred);

        void openRepo(Starred starred);

    }

}
