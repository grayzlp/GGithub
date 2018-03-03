package com.grayzlp.ggithub.core.module.follow;

import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.user.SimpleUser;

import java.util.List;

public class FollowContract {

    public interface View extends BaseView<Presenter> {
        void showUsers(List<SimpleUser> user);
        void showLoadError();
    }

    public interface Presenter extends BasePresenter<View> {
        void loadFollowingOfUser(String username, boolean forceUpdate);
        void loadFollowerOfUser(String username, boolean forceUpdate);
    }

}
