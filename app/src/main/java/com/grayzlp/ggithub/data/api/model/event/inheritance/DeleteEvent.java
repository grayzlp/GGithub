package com.grayzlp.ggithub.data.api.model.event.inheritance;

/**
 * Models a delete event.
 */

public class DeleteEvent {

    public DeleteEventPayload payload;

    public static class DeleteEventPayload {
        public String ref_type;
        public String ref;
    }
}
