package com.grayzlp.ggithub.core.module.people;


import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.List;

public class PeopleContract {

    interface View extends BaseView<Presenter>{

        void showPeople(List<SimpleUser> users);

        void showLoadingIndicator(boolean active);

        void showLoadingError();

        void showNoData();

    }

    interface Presenter extends BasePresenter<View> {

        void load(boolean forceUpdate);

        void setPeopleType(PeopleType type);
    }

}
