package com.grayzlp.ggithub.core.module.event;

import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.model.event.BaseEvent;

import java.util.List;

public class EventContract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void showLoadingError();

        void showNoEvent();

        void showEvents(List<BaseEvent> events);

        void showEventActor();

        void showEventDetail();

    }


    interface Presenter extends BasePresenter<View> {

        void loadEvents(boolean forceUpdate);

        void openEventActor(BaseEvent event);

        void openEventDetail(BaseEvent event);

        void takeView(EventContract.View view);

        void dropView();

    }
}
