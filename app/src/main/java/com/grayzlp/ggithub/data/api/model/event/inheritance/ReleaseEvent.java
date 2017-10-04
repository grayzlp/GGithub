package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.repo.Release;

/**
 * Models a ReleaseEvent
 */

public class ReleaseEvent extends BaseEvent{
    public static class ReleaseEventPayload {
        public String action;
        public Release release;
    }
}
