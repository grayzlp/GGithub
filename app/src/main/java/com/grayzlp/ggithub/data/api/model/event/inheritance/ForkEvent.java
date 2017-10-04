package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.repo.Repository;

/**
 * Models a ForkEvent.
 */

public class ForkEvent extends BaseEvent {

    public ForkEventPayload payload;

    public static class ForkEventPayload {
        Repository forkee;
    }
}
