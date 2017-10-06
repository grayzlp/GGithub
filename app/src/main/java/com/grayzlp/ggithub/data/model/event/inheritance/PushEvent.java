package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.repo.Commit;

/**
 * Models a PushEvent
 */

public class PushEvent extends BaseEvent {

    public PushEventPayload payload;

    public static class PushEventPayload {
        public String ref;
        public String head;
        public String before;
        public int size;
        public int distinct_size;
        public Commit[] commits;
        // TODO Fix other field
    }
}
