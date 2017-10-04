package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;

/**
 * Models a WatchEvent.
 */

public class WatchEvent extends BaseEvent{

    public WatchEventPayload payload;

    public static class WatchEventPayload {
        public String action;
    }

}
