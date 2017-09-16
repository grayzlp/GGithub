package com.grayzlp.ggithub.core.event;

import com.grayzlp.ggithub.core.BasePresenter;
import com.grayzlp.ggithub.core.BaseView;
import com.grayzlp.ggithub.data.api.model.event.BaseEvent;

public class EventContract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator(boolean active);

        void showLoadingError();

        void showNoEvent();

        void showEvents(BaseEvent[] events);

        void showEventActor();

        void showEventDetail();

    }


    interface Presenter extends BasePresenter {

        void loadEvents();

        void openEventActor(BaseEvent event);

        void openEventDetail(BaseEvent event);

    }
}
