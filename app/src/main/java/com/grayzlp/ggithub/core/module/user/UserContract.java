package com.grayzlp.ggithub.core.module.user;

import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.user.User;

public class UserContract {

    public interface View extends BaseView<Presenter> {

        void showUser(User user);

        void showLoadError();

    }

    public interface Presenter extends BasePresenter<View> {

        void loadUser(String username, boolean forceUpdate);
    }

}
